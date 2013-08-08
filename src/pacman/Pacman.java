/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacman.OptionParser.Args;
import pacman.OptionParser.Options;
import pacman.OptionParser.ParsedArgs;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Util;

/**
 *
 * @author archie
 */
public class Pacman {

    private final Logger logger = Logger.getLogger(getClass().getPackage().getName());
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
/*
 * FRAMEWORK TO START A GAME
 */

    private String getDefault(final String str) {
        return str + " [Default: %default]";
    }

    private Map<String, String> parseAgentArgs(final String str) {
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
    private Map<String, Object> readCommand(final String[] argv) {
        List<String> usageStr = new ArrayList<>();
        try {
            usageStr = Util.readSmallTextFile("usage.txt");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to open usage.txt file", ex);
        }
        //        from optparse import OptionParser
        final OptionParser parser = new OptionParser(Util.concatStringList(usageStr, "\n"));

        parser.add_option("-n", "--numGames", "numGames", "int", "the number of GAMES to play", "GAMES", 1, null);
        parser.add_option("-l", "--layout", "layout", null, "the LAYOUT_FILE from which to load the map layout", "LAYOUT_FILE", "mediumClassic", null);
        parser.add_option("-p", "--pacman", "pacman", null,  "the agent TYPE in the pacmanAgents module to use", "TYPE", "KeyboardAgent", null);
        parser.add_option("-t", "--textGraphics", "textGraphics", null, "Display output as text only", null, false, "store_true");
        parser.add_option("-q", "--quietTextGraphics", "quietGraphics", null, "Generate minimal output and no graphics", null, false, "store_true");
//        parser.add_option('-g', '--ghosts', dest='ghost',
//                          help=default('the ghost agent TYPE in the ghostAgents module to use'),
//                          metavar = 'TYPE', default='RandomGhost')
//        parser.add_option('-k', '--numghosts', type='int', dest='numGhosts',
//                          help=default('The maximum number of ghosts to use'), default=4)
//        parser.add_option('-z', '--zoom', type='float', dest='zoom',
//                          help=default('Zoom the size of the graphics window'), default=1.0)
//        parser.add_option('-f', '--fixRandomSeed', action='store_true', dest='fixRandomSeed',
//                          help='Fixes the random seed to always play the same game', default=False)
//        parser.add_option('-r', '--recordActions', action='store_true', dest='record',
//                          help='Writes game histories to a file (named by the time they were played)', default=False)
//        parser.add_option('--replay', dest='gameToReplay',
//                          help='A recorded game file (pickle) to replay', default=None)
//        parser.add_option('-a','--agentArgs',dest='agentArgs',
//                          help='Comma separated values sent to agent. e.g. "opt1=val1,opt2,opt3=val3"')
//        parser.add_option('-x', '--numTraining', dest='numTraining', type='int',
//                          help=default('How many episodes are training (suppresses output)'), default=0)
//        parser.add_option('--frameTime', dest='frameTime', type='float',
//                          help=default('Time to delay between frames; <0 means keyboard'), default=0.1)
//        parser.add_option('-c', '--catchExceptions', action='store_true', dest='catchExceptions',
//                          help='Turns on exception handling and timeouts during games', default=False)
//        parser.add_option('--timeout', dest='timeout', type='int',
//                          help=default('Maximum length of time an agent can spend computing in a single game'), default=30)

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

        Map<String, Object> args = new HashMap<>();
        // Choose a layout
        args.put("layout", Layout.getLayout( options.get("layout")));
        if(args.get("layout") == null) {
            throw new RuntimeException("The layout " + options.get("layout") + " cannot be found");
        }

        // Choose a Pacman agent
        final boolean noKeyboard =
                options.get("gameToReplay") == null && (options.contains("textGraphics") || options.contains("quietGraphics"));
        final Object pacmanType = loadAgent(options.get("pacman"), noKeyboard);
        final Map agentOpts = parseAgentArgs(options.get("agentArgs"));
        if(options.get("numTraining") > 0) {
            args.put("numTraining", options.get("numTraining"));
            if(!agentOpts.containsKey("numTraining")) {
                agentOpts.put("numTraining", options.get("numTraining"));
            }
        }
        Object pacman = pacmanType(agentOpts); // Instantiate Pacman with agentArgs
        args.put("pacman", pacman);

        // Don't display training games
        if(agentOpts.containsKey("numTrain")) {
            options.put("numQuiet", int(agentOpts.get("numTrain")));
            options.put("numIgnore", int(agentOpts.get("numTrain")));
        }

        // Choose a ghost agent
        Object ghostType = loadAgent(options.get("ghost"), noKeyboard);
        List<Object> ghostTypes = new ArrayList<>();
        for(int i=0; i<options.get("numGhosts"); i++) {
            ghostTypes.add(ghostType(i+1));
        }
        args.put("ghosts", ghostTypes);

        // Choose a display format
        if(options.contains("quietGraphics")) {
            //import textDisplay
            args.put("display", textDisplay.NullGraphics());
        } else if(options.contains("textGraphics") {
            // import textDisplay
            textDisplay.setSleepTime(options.get("frameTime"));
            args.set("display", textDisplay.PacmanGraphics());
        } else {
            // import graphicsDisplay
            args.set("display", graphicsDisplay.PacmanGraphics(options.get("zoom"), frameTime = options.get("frameTime")));
        }
        args.put("numGames", options.get("numGames"));
        args.put("record", options.get("record"));
        args.put("catchExceptions", options.get("catchExceptions"));
        args.put("timeout", options.get("timeout"));

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
            throw new NotImplementedException();
        }

        return args;
    }

    private Object loadAgent(final AgentDirectory agentName, final Object nographics) {
        // Looks through all pythonPath Directories for the right module,
//        pythonPathStr = os.path.expandvars("$PYTHONPATH")
//        if pythonPathStr.find(';') == -1:
//            pythonPathDirs = pythonPathStr.split(':')
//        else:
//            pythonPathDirs = pythonPathStr.split(';')
//        pythonPathDirs.append('.')

//        for moduleDir in pythonPathDirs:
//            if not os.path.isdir(moduleDir): continue
//            moduleNames = [f for f in os.listdir(moduleDir) if f.endswith('gents.py')]
//            for modulename in moduleNames:
//                try:
//                    module = __import__(modulename[:-3])
//                except ImportError:
//                    continue
//                if agentName in dir(module):
//                    if nographics and modulename == 'keyboardAgents.py':
//                        raise Exception('Using the keyboard requires graphics (not text display)')
//                    return getattr(module, agentName)
//        raise Exception('The agent ' + agentName + ' is not specified in any *Agents.py.')
        
        // Python code establishes the Python paths, and the current working directory.
        // Loops over the paths, finding the directories
        // Finds the files (aka modules) in each directory ending with "gents.py"
        // For each of these modules, imports the module, and looks for an object
        // in the module called 'agentName'.  
        // If agentName is found in the module 'keyboardAgents.py' and 'nographics' is specified,
        // then raise an error (Using the keyboard requires graphics, not text display).
        // Return a reference to the located agentName, which is a class.
        // Example, RandomGhost, which extends GhostAgent, which extends Agent.
        
        
        switch(agentName) {
            default:
                throw new RuntimeException("Unhandled agent name");
        }
    }

//    private void replayGame(final Layout layout, final Object actions, final Object display) {
//        final ClassicGameRules rules = new ClassicGameRules();
//        final List<Agent> agents = new ArrayList();
//        agents.add(new GreedyAgent());
//        for(int i=0; i<layout.getNumGhosts(); i++) {
//            agents.add(new RandomGhost(i+1));
//        }
//        final Game game = rules.newGame(layout, agents.get(0), agents.subList(1, agents.size()), display);
//        GameState state = game.getState();
//        display.initialize(state.getData());
//
//        for(Action action : actions) {
//            // Execute the action
//            state = state.generateSuccessor( *action );
//            # Change the display
//            display.update( state.data )
//            # Allow for game specific conditions (winning, losing, etc.)
//            rules.process(state, game)
//
//        display.finish()

    private void runGames(
            final Layout layout,
            final Object pacman,
            final Object ghosts,
            final Object display,
            final int numGames,
            final Object record,
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
        final Object mainDisplay = display;

        final ClassicGameRules rules = new ClassicGameRules(timeout);
        final List games = new ArrayList();

        for(int i=0; i<numGames; i++) {
            final boolean beQuiet = i < numTraining;
            final Object gameDisplay;
            if(beQuiet) {
                // Suppress output and graphics
                //import textDisplay
                gameDisplay = textDisplay.NullGraphics();
                rules.setQuiet(true);
            } else {
                gameDisplay = display;
                rules.setQuiet(false);
            }
            game = rules.newGame( layout, pacman, ghosts, gameDisplay, beQuiet, catchExceptions)
            game.run()
            if not beQuiet: games.append(game)

            if record:
                import time, cPickle
                fname = ('recorded-game-%d' % (i + 1)) +  '-'.join([str(t) for t in time.localtime()[1:6]])
                f = file(fname, 'w')
                components = {'layout': layout, 'actions': game.moveHistory}
                cPickle.dump(components, f)
                f.close()

        if (numGames-numTraining) > 0:
            scores = [game.state.getScore() for game in games]
            wins = [game.state.isWin() for game in games]
            winRate = wins.count(True)/ float(len(wins))
            print 'Average Score:', sum(scores) / float(len(scores))
            print 'Scores:       ', ', '.join([str(score) for score in scores])
            print 'Win Rate:      %d/%d (%.2f)' % (wins.count(True), len(wins), winRate)
            print 'Record:       ', ', '.join([ ['Loss', 'Win'][int(w)] for w in wins])

        return games

if __name__ == '__main__':
    args = readCommand( sys.argv[1:] ) # Get game components based on input
    runGames( **args )

    # import cProfile
    # cProfile.run("runGames( **args )")
    pass

}
