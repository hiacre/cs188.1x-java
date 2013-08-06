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
    private void readCommand(final String[] argv) {
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
        pacman = pacmanType(**agentOpts) # Instantiate Pacman with agentArgs
        args['pacman'] = pacman

        # Don't display training games
        if 'numTrain' in agentOpts:
            options.numQuiet = int(agentOpts['numTrain'])
            options.numIgnore = int(agentOpts['numTrain'])

        # Choose a ghost agent
        ghostType = loadAgent(options.ghost, noKeyboard)
        args['ghosts'] = [ghostType( i+1 ) for i in range( options.numGhosts )]

        # Choose a display format
        if options.quietGraphics:
            import textDisplay
            args['display'] = textDisplay.NullGraphics()
        elif options.textGraphics:
            import textDisplay
            textDisplay.SLEEP_TIME = options.frameTime
            args['display'] = textDisplay.PacmanGraphics()
        else:
            import graphicsDisplay
            args['display'] = graphicsDisplay.PacmanGraphics(options.zoom, frameTime = options.frameTime)
        args['numGames'] = options.numGames
        args['record'] = options.record
        args['catchExceptions'] = options.catchExceptions
        args['timeout'] = options.timeout

        # Special case: recorded games don't use the runGames method or args structure
        if options.gameToReplay != None:
            print 'Replaying recorded game %s.' % options.gameToReplay
            import cPickle
            f = open(options.gameToReplay)
            try: recorded = cPickle.load(f)
            finally: f.close()
            recorded['display'] = args['display']
            replayGame(**recorded)
            sys.exit(0)

        return args

def loadAgent(pacman, nographics):
    # Looks through all pythonPath Directories for the right module,
    pythonPathStr = os.path.expandvars("$PYTHONPATH")
    if pythonPathStr.find(';') == -1:
        pythonPathDirs = pythonPathStr.split(':')
    else:
        pythonPathDirs = pythonPathStr.split(';')
    pythonPathDirs.append('.')

    for moduleDir in pythonPathDirs:
        if not os.path.isdir(moduleDir): continue
        moduleNames = [f for f in os.listdir(moduleDir) if f.endswith('gents.py')]
        for modulename in moduleNames:
            try:
                module = __import__(modulename[:-3])
            except ImportError:
                continue
            if pacman in dir(module):
                if nographics and modulename == 'keyboardAgents.py':
                    raise Exception('Using the keyboard requires graphics (not text display)')
                return getattr(module, pacman)
    raise Exception('The agent ' + pacman + ' is not specified in any *Agents.py.')

def replayGame( layout, actions, display ):
    import pacmanAgents, ghostAgents
    rules = ClassicGameRules()
    agents = [pacmanAgents.GreedyAgent()] + [ghostAgents.RandomGhost(i+1) for i in range(layout.getNumGhosts())]
    game = rules.newGame( layout, agents[0], agents[1:], display )
    state = game.state
    display.initialize(state.data)

    for action in actions:
            # Execute the action
        state = state.generateSuccessor( *action )
        # Change the display
        display.update( state.data )
        # Allow for game specific conditions (winning, losing, etc.)
        rules.process(state, game)

    display.finish()

def runGames( layout, pacman, ghosts, display, numGames, record, numTraining = 0, catchExceptions=False, timeout=30 ):
    import __main__
    __main__.__dict__['_display'] = display

    rules = ClassicGameRules(timeout)
    games = []

    for i in range( numGames ):
        beQuiet = i < numTraining
        if beQuiet:
                # Suppress output and graphics
            import textDisplay
            gameDisplay = textDisplay.NullGraphics()
            rules.quiet = True
        else:
            gameDisplay = display
            rules.quiet = False
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
