package graphics.utils;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author archie
 */
public class GraphicsUtils {
//_Windows = sys.platform == 'win32'  # True if on Win95/98/NT
//
//    /** The root windows for graphics output */
//    private static Object _root_window = null;
//    private static String _bg_color;
//    /** The canvas which holds graphics */
//    _canvas = null;
//    /** Size of canvas object */
//    private static int _canvas_xs;
//    private static int _canvas_ys;
//    /** Current position on canvas */
//    private static int _canvas_x;
//    private static int _canvas_y;
//    /** Current colour (set to black below) */
//    _canvas_col = null;
//    _canvas_tsize = 12;
//    _canvas_tserifs = 0;
//
//    
    public static String formatColor(final double r, final double g, final double b) {
        if(r<0 || g<0 || b<0 || r>1 || g>1 || b>0) {
            throw new RuntimeException("All components must be between 0 and 1 inclusive");
        }
        final StringBuilder sb = new StringBuilder();
        final int red = (int)r*255;
        final int green = (int)g*255;
        final int blue = (int)b*255;
        
        sb.append("#").append(hex(red)).append(hex(green)).append(hex(blue));
        return sb.toString();
    }
    
    
    
    /** Takes an integer between 0 and 255 (inclusive) and returns its zero-left-padded hex string representation. */
    private static String hex(final int colour) {
        final String hex = Integer.toHexString(colour);
        if(hex.length()<2) {
            return "0" + hex;
        } else {
            return hex;
        }
    }

    /** Takes a colour in hex notation (e.g. #abcdef) and returns it as a vector
     * of numbers between 0 and 1 (fractions of 255).
     */
    public static List<Double> colorToVector(final String color) {
        final String hexRed = color.substring(1, 3);
        final String hexGreen = color.substring(3, 5);
        final String hexBlue = color.substring(5, 7);
        
        return Arrays.asList(
                Integer.parseInt(hexRed, 16) / 256.0,
                Integer.parseInt(hexGreen, 16) / 256.0,
                Integer.parseInt(hexBlue, 16) / 256.0);
    }
    
    /** Writes the current canvas to a postscript file. */
    public static void writePostscript(final String filename) {
        psfile = file(filename, 'w');
        psfile.write(_canvas.postscript(pageanchor='sw',
                         y='0.c',
                         x='0.c'));
        psfile.close();
    }

////if _Windows:
////    _canvas_tfonts = ['times new roman', 'lucida console']
////else:
////    _canvas_tfonts = ['times', 'lucidasans-24']
////    pass # XXX need defaults here
//
//    private void sleep(secs) {
//        if(this._root_window == null) {
//            time.sleep(secs);
//        } else {
//            this._root_window.update_idletasks();
//            this._root_window.after(int(1000 * secs), _root_window.quit);
//            this._root_window.mainloop();
//        }
//    }
//
//    private static void begin_graphics(Integer width, Integer height, String color, final String title) {
//        width = width == null ? 640 : width;
//        height = height == null ? 480 : height;
//        color = color == null ? formatColor(0,0,0) : color;
//        //global _root_window, _canvas, _canvas_x, _canvas_y, _canvas_xs, _canvas_ys, _bg_color
//
//        // Check for duplicate call
//        if(_root_window != null) {
//            // Lose the window.
//            _root_window.destroy();
//        }
//
//        // Save the canvas size parameters
//        _canvas_xs = width - 1;
//        _canvas_ys = height - 1;
//        _canvas_x = 0;
//        _canvas_y = _canvas_ys;
//        _bg_color = color
//
//        // Create the root window
//        _root_window = Tkinter.Tk();
//        _root_window.protocol('WM_DELETE_WINDOW', _destroy_window);
//        _root_window.title(title or 'Graphics Window');
//        _root_window.resizable(0, 0);
//
//        # Create the canvas object
//        try:
//            _canvas = Tkinter.Canvas(_root_window, width=width, height=height)
//            _canvas.pack()
//            draw_background()
//            _canvas.update()
//        except:
//            _root_window = None
//            raise
//
//        # Bind to key-down and key-up events
//        _root_window.bind( "<KeyPress>", _keypress )
//        _root_window.bind( "<KeyRelease>", _keyrelease )
//        _root_window.bind( "<FocusIn>", _clear_keys )
//        _root_window.bind( "<FocusOut>", _clear_keys )
//        _root_window.bind( "<Button-1>", _leftclick )
//        _root_window.bind( "<Button-2>", _rightclick )
//        _root_window.bind( "<Button-3>", _rightclick )
//        _root_window.bind( "<Control-Button-1>", _ctrl_leftclick)
//        _clear_keys()
//
//_leftclick_loc = None
//_rightclick_loc = None
//_ctrl_leftclick_loc = None
//
//def _leftclick(event):
//    global _leftclick_loc
//    _leftclick_loc = (event.x, event.y)
//
//def _rightclick(event):
//    global _rightclick_loc
//    _rightclick_loc = (event.x, event.y)
//
//def _ctrl_leftclick(event):
//    global _ctrl_leftclick_loc
//    _ctrl_leftclick_loc = (event.x, event.y)
//
//def wait_for_click():
//    while True:
//        global _leftclick_loc
//        global _rightclick_loc
//        global _ctrl_leftclick_loc
//        if _leftclick_loc != None:
//            val = _leftclick_loc
//            _leftclick_loc = None
//            return val, 'left'
//        if _rightclick_loc != None:
//            val = _rightclick_loc
//            _rightclick_loc = None
//            return val, 'right'
//        if _ctrl_leftclick_loc != None:
//            val = _ctrl_leftclick_loc
//            _ctrl_leftclick_loc = None
//            return val, 'ctrl_left'
//        sleep(0.05)
//
//def draw_background():
//    corners = [(0,0), (0, _canvas_ys), (_canvas_xs, _canvas_ys), (_canvas_xs, 0)]
//    polygon(corners, _bg_color, fillColor=_bg_color, filled=True, smoothed=False)
//
//def _destroy_window(event=None):
//    sys.exit(0)
//#    global _root_window
//#    _root_window.destroy()
//#    _root_window = None
//    #print "DESTROY"
//
//def end_graphics():
//    global _root_window, _canvas, _mouse_enabled
//    try:
//        try:
//            sleep(1)
//            if _root_window != None:
//                _root_window.destroy()
//        except SystemExit, e:
//            print 'Ending graphics raised an exception:', e
//    finally:
//        _root_window = None
//        _canvas = None
//        _mouse_enabled = 0
//        _clear_keys()
//
//def clear_screen(background=None):
//    global _canvas_x, _canvas_y
//    _canvas.delete('all')
//    draw_background()
//    _canvas_x, _canvas_y = 0, _canvas_ys
//
//def polygon(coords, outlineColor, fillColor=None, filled=1, smoothed=1, behind=0, width=1):
//    c = []
//    for coord in coords:
//        c.append(coord[0])
//        c.append(coord[1])
//    if fillColor == None: fillColor = outlineColor
//    if filled == 0: fillColor = ""
//    poly = _canvas.create_polygon(c, outline=outlineColor, fill=fillColor, smooth=smoothed, width=width)
//    if behind > 0:
//        _canvas.tag_lower(poly, behind) # Higher should be more visible
//    return poly

    public static Object square(final double x, final double y, final double r, final String color, Integer filled, Integer behind) {
        filled = filled == null ? 1 : filled;
        behind = behind == null ? 0 : behind;
        List coords = Arrays.asList(
                Arrays.asList(x - r, y - r),
                Arrays.asList(x + r, y - r),
                Arrays.asList(x + r, y + r),
                Arrays.asList(x - r, y + r));
        return polygon(coords, color, color, filled, 0, behind);
    }

    public static Object circle(
            final double x,
            final double y,
            final double r,
            final String outlineColor,
            final String fillColor,
            final List<Integer> endpoints,
            String style,
            Integer width) {
        style = style == null ? "pieslice" : style;
        width = width == null ? 2 : width;
        final double x0 = x-r-1;
        final double x1 = x+r;
        final double y0 = y-r-1;
        final double y1 = y+r;
        final List<Integer> e;
        if(endpoints == null) {
            e = Arrays.asList(0, 359);
        } else {
            e = endpoints;
        }
        while(e.get(0) > e.get(1)) {
            e.add(1, e.get(1) + 360);
        }

        return _canvas.create_arc(x0, y0, x1, y1, outlineColor, fillColor,
                                  e[1] - e[0], e[0], style, width);
    }

//def image(pos, file="../../blueghost.gif"):
//    x, y = pos
//    # img = PhotoImage(file=file)
//    return _canvas.create_image(x, y, image = Tkinter.PhotoImage(file=file), anchor = Tkinter.NW)


    public static void refresh() {
        _canvas.update_idletasks();
    }

//def moveCircle(id, pos, r, endpoints=None):
//    global _canvas_x, _canvas_y
//
//    x, y = pos
//#    x0, x1 = x - r, x + r + 1
//#    y0, y1 = y - r, y + r + 1
//    x0, x1 = x - r - 1, x + r
//    y0, y1 = y - r - 1, y + r
//    if endpoints == None:
//        e = [0, 359]
//    else:
//        e = list(endpoints)
//    while e[0] > e[1]: e[1] = e[1] + 360
//
//    edit(id, ('start', e[0]), ('extent', e[1] - e[0]))
//    move_to(id, x0, y0)
//
//def edit(id, *args):
//    _canvas.itemconfigure(id, **dict(args))

    public static Object text(
            final double x,
            final double y,
            final String color,
            final String contents,
            String font,
            Integer size,
            String style,
            String anchor) {
        font = font == null ? "Helvetica" : font;
        size = size == null ? 12 : size;
        style = style == null ? "normal" : style;
        anchor = anchor == null ? "nw" : anchor;
        font = (font, str(size), style);
        return _canvas.create_text(x, y, fill=color, text=contents, font=font, anchor=anchor);
    }

    public static void changeText(final Object id, final String newText, String font, Integer size, String style) {
        size = size == null ? 12 : size;
        style = style == null ? "normal" : style;
        _canvas.itemconfigure(id, text=newText);
        if(font != null) {
            _canvas.itemconfigure(id, font=(font, '-%d' % size, style));
        }
    }

//def changeColor(id, newColor):
//    _canvas.itemconfigure(id, fill=newColor)
//
//def line(here, there, color=formatColor(0, 0, 0), width=2):
//    x0, y0 = here[0], here[1]
//    x1, y1 = there[0], there[1]
//    return _canvas.create_line(x0, y0, x1, y1, fill=color, width=width)
//
//##############################################################################
//### Keypress handling ########################################################
//##############################################################################
//
//# We bind to key-down and key-up events.
//
//_keysdown = {}
//_keyswaiting = {}
//# This holds an unprocessed key release.  We delay key releases by up to
//# one call to keys_pressed() to get round a problem with auto repeat.
//_got_release = None
//
//def _keypress(event):
//    global _got_release
//    #remap_arrows(event)
//    _keysdown[event.keysym] = 1
//    _keyswaiting[event.keysym] = 1
//#    print event.char, event.keycode
//    _got_release = None
//
//def _keyrelease(event):
//    global _got_release
//    #remap_arrows(event)
//    try:
//        del _keysdown[event.keysym]
//    except:
//        pass
//    _got_release = 1
//
//def remap_arrows(event):
//    # TURN ARROW PRESSES INTO LETTERS (SHOULD BE IN KEYBOARD AGENT)
//    if event.char in ['a', 's', 'd', 'w']:
//        return
//    if event.keycode in [37, 101]: # LEFT ARROW (win / x)
//        event.char = 'a'
//    if event.keycode in [38, 99]: # UP ARROW
//        event.char = 'w'
//    if event.keycode in [39, 102]: # RIGHT ARROW
//        event.char = 'd'
//    if event.keycode in [40, 104]: # DOWN ARROW
//        event.char = 's'
//
//def _clear_keys(event=None):
//    global _keysdown, _got_release, _keyswaiting
//    _keysdown = {}
//    _keyswaiting = {}
//    _got_release = None
//
//def keys_pressed(d_o_e=Tkinter.tkinter.dooneevent,
//                 d_w=Tkinter.tkinter.DONT_WAIT):
//    d_o_e(d_w)
//    if _got_release:
//        d_o_e(d_w)
//    return _keysdown.keys()
//
//def keys_waiting():
//    global _keyswaiting
//    keys = _keyswaiting.keys()
//    _keyswaiting = {}
//    return keys
//
//# Block for a list of keys...
//
//def wait_for_keys():
//    keys = []
//    while keys == []:
//        keys = keys_pressed()
//        sleep(0.05)
//    return keys

    public static void remove_from_screen(final Object x, Object d_o_e, Object d_w) {
        d_o_e = d_o_e == null ? Tkinter.tkinter.dooneevent : d_o_e;
        d_w = d_w == null ? Tkinter.tkinter.DONT_WAIT : d_w;

        _canvas.delete(x);
        d_o_e(d_w);
    }

//def _adjust_coords(coord_list, x, y):
//    for i in range(0, len(coord_list), 2):
//        coord_list[i] = coord_list[i] + x
//        coord_list[i + 1] = coord_list[i + 1] + y
//    return coord_list
//
//def move_to(object, x, y=None,
//            d_o_e=Tkinter.tkinter.dooneevent,
//            d_w=Tkinter.tkinter.DONT_WAIT):
//    if y is None:
//        try: x, y = x
//        except: raise  'incomprehensible coordinates'
//
//    horiz = True
//    newCoords = []
//    current_x, current_y = _canvas.coords(object)[0:2] # first point
//    for coord in  _canvas.coords(object):
//        if horiz:
//            inc = x - current_x
//        else:
//            inc = y - current_y
//        horiz = not horiz
//
//        newCoords.append(coord + inc)
//
//    _canvas.coords(object, *newCoords)
//    d_o_e(d_w)
//
//def move_by(object, x, y=None,
//            d_o_e=Tkinter.tkinter.dooneevent,
//            d_w=Tkinter.tkinter.DONT_WAIT, lift=False):
//    if y is None:
//        try: x, y = x
//        except: raise Exception, 'incomprehensible coordinates'
//
//    horiz = True
//    newCoords = []
//    for coord in  _canvas.coords(object):
//        if horiz:
//            inc = x
//        else:
//            inc = y
//        horiz = not horiz
//
//        newCoords.append(coord + inc)
//
//    _canvas.coords(object, *newCoords)
//    d_o_e(d_w)
//    if lift:
//        _canvas.tag_raise(object)
//
//
//ghost_shape = [
//    (0, - 0.5),
//    (0.25, - 0.75),
//    (0.5, - 0.5),
//    (0.75, - 0.75),
//    (0.75, 0.5),
//    (0.5, 0.75),
//    (- 0.5, 0.75),
//    (- 0.75, 0.5),
//    (- 0.75, - 0.75),
//    (- 0.5, - 0.5),
//    (- 0.25, - 0.75)
//  ]
//
//if __name__ == '__main__':
//    begin_graphics()
//    clear_screen()
//    ghost_shape = [(x * 10 + 20, y * 10 + 20) for x, y in ghost_shape]
//    g = polygon(ghost_shape, formatColor(1, 1, 1))
//    move_to(g, (50, 50))
//    circle((150, 150), 20, formatColor(0.7, 0.3, 0.0), endpoints=[15, - 15])
//    sleep(2)
//    
}
