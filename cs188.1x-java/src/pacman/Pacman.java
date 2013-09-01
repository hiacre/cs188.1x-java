package pacman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacman.OptionParser.Args;
import pacman.OptionParser.Options;
import pacman.OptionParser.ParsedArgs;
import util.Util;

/**
 *
 * @author archie
 */
public class Pacman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        final List<String> argList = Arrays.asList(args);
        final Command mapArgs = readCommand(argList.subList(1, argList.size()) ); // Get game components based on input
        runGames(
                mapArgs.getLayout(),
                mapArgs.getPacman(),
                mapArgs.getGhosts(),
                mapArgs.getDisplay(),
                mapArgs.getNumGames(),
                mapArgs.getRecord(),
                mapArgs.getNumTraining(),
                mapArgs.isCatchExceptions(),
                mapArgs.getTimeout());
    }
    
    /*
     * FRAMEWORK TO START A GAME
    */

    private static Map<String, String> parseAgentArgs(final String str) {
        if(str == null) {
            return new HashMap();
        }
        final String[] pieces = str.split(",");
        final Map<String, String> opts = new HashMap();
        for(String p : pieces) {
            final String key;
            final String val;
            if(p.contains("=")) {
                final String[] pair = p.split("=");
                key = pair[0];
                val = pair[1];
            } else {
                key = p;
                val = "1";
            }
            opts.put(key, val);
        }
        return opts;
    }

    /** Processes the command used to run pacman from the command line. */
    private static Command readCommand(final List<String> argv) {
        List<String> usageStr = new ArrayList<>();
        try {
            usageStr = Util.readSmallTextFile("usage.txt");
        } catch (IOException ex) {
            final Logger logger2 = Logger.getLogger("pacman");
            logger2.log(Level.SEVERE, "Failed to open usage.txt file", ex);
        }
        //        from optparse import OptionParser
        final OptionParser parser = new OptionParser(Util.concatStringList(usageStr, "\n"));

        parser.add_option("-n", "--numGames", "numGames", "int", "the number of GAMES to play", "GAMES", 1, null);
        parser.add_option("-l", "--layout", "layout", null, "the LAYOUT_FILE from which to load the map layout", "LAYOUT_FILE", "mediumClassic", null);
        parser.add_option("-p", "--pacman", "pacman", null,  "the agent TYPE in the pacmanAgents module to use", "TYPE", "KeyboardAgent", null);
        parser.add_option("-t", "--textGraphics", "textGraphics", null, "Display output as text only", null, false, "store_true");
        parser.add_option("-q", "--quietTextGraphics", "quietGraphics", null, "Generate minimal output and no graphics", null, false, "store_true");
        parser.add_option("-g", "--ghosts", "dest", null, "the ghost agent TYPE in the ghostAgents module to use", "TYPE", "RandomGhost", null);
        parser.add_option("-k", "--numGhosts", "numGhosts", "int", "The maximum number of ghosts to use", null, 4, null);
        parser.add_option("-z", "--zoom", "zoom", "float", "Zoom the size of the graphics window", null, 1.0, null);
        parser.add_option("-f", "--fixRandomSeed", "fixRandomSeed", null, "Fixes the random seed to always play the same game", null, false, null);
        parser.add_option("-r", "--recordActions", "record", null, "Writes game histories to a file (named by the time they were played)", null, false, "store_true");
        parser.add_option(null, "--replay", "gameToReplay", null, "A recorded game file (pickle) to replay", null, null, null);
        parser.add_option("-a", "--agentArgs", "agentArgs", null, "Comma separated values sent to agent. e.g. 'opt1=val1,opt2,opt3=val3'", null, null, null);
        parser.add_option("-x", "--numTraining", "numTraining", "int", "How many episodes are training (suppresses output)", null, 0, null);
        parser.add_option(null, "--frameTime", "frameTime", "float", "Time to delay between frames; <0 means keyboard", null, 0.1, null);
        parser.add_option("-c", "--catchExceptions", "catchExceptions", null, "Turns on exception handling and timeouts during games", null, false, "store_true");
        parser.add_option(null, "--timeout", "timeout", "int", "Maximum length of time an agent can spend computing in a single game", null, 30, null);

        final ParsedArgs parsedArgs = parser.parse_args(argv);
        final Options options = parsedArgs.getOptions();
        final Args otherjunk = parsedArgs.getArgs();
        
        if(!otherjunk.isEmpty()) {
            throw new RuntimeException("Command line input not understood: " + otherjunk.toString());
        }
        //args = dict()

        // Fix the random seed
        final Random random = new Random();
        if(options.contains("fixRandomSeed")) {
            random.setSeed(188);
        }

        final Command args = new Command();
        // Choose a layout
        args.setLayout(Layout.getLayout( options.get("layout")));
        if(args.getLayout() == null) {
            throw new RuntimeException("The layout " + options.get("layout") + " cannot be found");
        }

        // Choose a Pacman agent
        final boolean noKeyboard =
                options.get("gameToReplay") == null && (options.contains("textGraphics") || options.contains("quietGraphics"));
        final AgentFactoryPacman pacmanType = loadAgentPacman(AgentDirectoryPacman.valueOf(options.get("pacman")), noKeyboard);
        final Map agentOpts = parseAgentArgs(options.get("agentArgs"));
        if(Integer.parseInt(options.get("numTraining")) > 0) {
            args.setNumTraining(Integer.parseInt(options.get("numTraining")));
            if(!agentOpts.containsKey("numTraining")) {
                agentOpts.put("numTraining", options.get("numTraining"));
            }
        }
        final Agent pacman = pacmanType.make();  // TODO Instantiate Pacman with agentArgs
        args.setPacman(pacman);

        // Don't display training games
        if(agentOpts.containsKey("numTrain")) {
            options.setNumQuiet((int)agentOpts.get("numTrain"));
            options.setNumIgnore((int)agentOpts.get("numTrain"));
        }

        // Choose a ghost agent
        final AgentFactoryGhost ghostType = loadAgentGhost(AgentDirectoryGhost.valueOf(options.get("ghost")));
        final List<GhostAgent> ghostTypes = new ArrayList<>();
        for(int i=0; i<Integer.parseInt(options.get("numGhosts")); i++) {
            ghostTypes.add(ghostType.make(i+1));
        }
        args.setGhosts(ghostTypes);

        // Choose a display format
        if(options.contains("quietGraphics")) {
            args.setDisplay(new NullGraphics(null, null));
        } else if(options.contains("textGraphics")) {
            textDisplay.setSleepTime(options.get("frameTime"));
            args.setDisplay(new PacmanGraphicsText(null));
        } else {
            args.set("display", new PacmanGraphicsNonText(options.get("zoom"), options.get("frameTime")));
        }
        args.setNumGames(Integer.parseInt(options.get("numGames")));
        args.setRecord(options.get("record"));
        args.setCatchExceptions(options.get("catchExceptions"));
        args.setTimeout(options.get("timeout"));

        // Special case: recorded games don't use the runGames method or args structure
        if(options.get("gameToReplay") != null) {
            System.out.println("Replaying recorded game" + options.get("gameToReplay"));
            //import cPickle
//            f = open(options.gameToReplay)
//            try: recorded = cPickle.load(f)
//            finally: f.close()
//            recorded['display'] = args['display']
//            replayGame(**recorded)
//            sys.exit(0)
            throw new UnsupportedOperationException();
        }

        return args;
    }

    private static AgentFactoryPacman loadAgentPacman(final AgentDirectoryPacman agentName, final boolean nographics) {
        // Python code establishes the Python paths, and the current working directory.
        // Loops over the paths, finding the directories
        // Finds the files (aka modules) in each directory ending with "gents.py"
        // For each of these modules, imports the module, and looks for an object
        // in the module called 'agentName'.  
        // If agentName is found in the module 'keyboardAgents.py' and 'nographics' is specified,
        // then raise an error (Using the keyboard requires graphics, not text display).
        // Return a reference to the located agentName, which is a class.
        // Example, RandomGhost, which extends GhostAgent, which extends Agent.
        if(nographics) {
            switch(agentName) {
                case KeyboardAgent:
                case KeyboardAgent2:
                    throw new RuntimeException("A KeyboardAgent has been specified, but nographics has been specified");
            }
            
        }
        
        return agentName.getFactory();
    }
    
    private static AgentFactoryGhost loadAgentGhost(final AgentDirectoryGhost agentName) {
        return agentName.getFactory();
    }
    
    private void replayGame(final Layout layout, final List<Direction> actions, final PacmanDisplay display) {
        final ClassicGameRules rules = new ClassicGameRules();
        final List<Agent> agents = new ArrayList();
        agents.add(new GreedyAgent());
        for(int i=0; i<layout.getNumGhosts(); i++) {
            agents.add(new RandomGhost(i+1));
        }
        final Game game = rules.newGame(layout, agents.get(0), agents.subList(1, agents.size()), display);
        GameState1 state = game.getState();
        display.initialize(state, null);

        for(Direction action : actions) {
            // Execute the action
            state = state.generateSuccessor(null, action);
            // Change the display
            display.update(state);
            // Allow for game specific conditions (winning, losing, etc.)
            rules.process(state, game);
        }

        display.finish();
    }

    private static List<Game> runGames(
            final Layout layout,
            final Agent pacman,
            final List<GhostAgent> ghosts,
            final PacmanDisplay display,
            final int numGames,
            final boolean record,
            Integer numTraining,
            Boolean catchExceptions,
            Integer timeout) {
        if(numTraining == null) {
            numTraining = 0;
        }
        if(catchExceptions == null) {
            catchExceptions = false;
        }
        if(timeout == null) {
            timeout = 30;
        }
        
        // TODO mainDisplay is intended to be an application global variable.
        // search the project for other instances of mainDisplay.
        final Object mainDisplay = display;

        final ClassicGameRules rules = new ClassicGameRules(timeout);
        final List<Game> games = new ArrayList();

        for(int i=0; i<numGames; i++) {
            final boolean beQuiet = i < numTraining;
            final PacmanDisplay gameDisplay;
            if(beQuiet) {
                // Suppress output and graphics
                //import textDisplay
                gameDisplay = new NullGraphics(null, null);
                rules.setQuiet(true);
            } else {
                gameDisplay = display;
                rules.setQuiet(false);
            }
            final Game game = rules.newGame(layout, pacman, ghosts, gameDisplay, beQuiet, catchExceptions);
            game.run();
            if(!beQuiet) {
                games.add(game);
            }

            if(record) {
                //import time, cPickle;
                final StringBuilder fname = new StringBuilder();
                fname.append("record-game-").append(i+1);
                // TODO now add on month-day-hour-minute-seconds
                throw new UnsupportedOperationException();
                //fname = ('recorded-game-%d' % (i + 1)) +  '-'.join([str(t) for t in time.localtime()[1:6]]);
//                f = file(fname, 'w');
//                components = {'layout': layout, 'actions': game.moveHistory};
//                cPickle.dump(components, f);
//                f.close();
            }
        }

        if(numGames-numTraining > 0) {
            final List<Integer> scores = new ArrayList<>();
            final List<Boolean> wins = new ArrayList<>();
            for(Game g : games) {
                scores.add(g.getState().getScore());
                wins.add(g.getState().isWin());
            }
            final double winRate = Collections.frequency(wins, true) / wins.size();
            System.out.println("Average Score: " + sum(scores) / scores.size());
            System.out.println("Scores:        " + Util.concatStringList(scores, ", "));
            final StringBuilder msgWinRate = new StringBuilder();
            msgWinRate.append("Win Rate:      ").append(Collections.frequency(wins, true));
            msgWinRate.append("/").append(wins.size()).append(winRate);
            System.out.println(msgWinRate);
            final StringBuilder msgRecord = new StringBuilder();
            msgRecord.append("Record:       ");
            for(Boolean w : wins) {
                msgRecord.append(w ? "Win " : "Loss ");
            }
            System.out.println(msgRecord.toString());
        }

        return games;
    }
        
    private static int sum(final Iterable<Integer> iterable) {
        int sum = 0;
        for(int i : iterable) {
            sum += i;
        }
        return sum;
    }

}
