//v1
package com.karmorak.api;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;

//import org.jnativehook.GlobalScreen;
//import org.jnativehook.NativeHookException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.files.FileManager;
import com.karmorak.api.listeners.ConsoleOutputCapturer;

public class Main {
	
//TODO
	/*  FIX THE FUCKING FONT BUGS!!!!
	 * 
//CHANGELOG
	 * 	1.5.1 r12
	 * 		- FileManagerDesktop: custom path & bug fix
	 * 	1.5.2 r13
	 * 		- Button Scrolables are now multi res (bug fix)
	 *	1.5.3 r14
	 * 		- Scrolable button bug fixes
	 * 		- colorize & getscale2 methoden in GameFunktions hinzugefügt 
	 *  1.5.4 r15
	 * 		- Scrolable B: custom Background, many optimizations, border_option
	 * 	1.6 r16
	 * 		- makegrayscale, invertcolor und createcolorbar hinzugefügt
	 * 		- scrolbar bigfixes
	 *  1.6.1 r17
	 * 		- added getScaleMulti & fixed getScaleMultiX and X2
	 * 		- added getRandomColor
	 *  1.6.2 r18
	 *  	- added colorize2
	 *  1.6.3 r19
	 *  	- fixed Scrolbutton(again)
	 *  	- fixed FileManagerDesktop it now creates the file if it doesn't exists and getString(name, path) added
	 *      - hangs are now relative to their Button
	 *      - hangs have an variable called "father" that is the id of their hanged button
	 *  1.6.4 r20
	 *  	- added ConsoleOuputCapturer
	 *  	- added FileManagerDesktop.write(line, path) 
	 *  1.7.0 r21
	 *  	- Button implements now Comparable
	 *  	- changed Position to Vector2
	 *  	- added Vector3
	 *  	- scrolable can now be vertical
	 *  	- customizable width on scrolable button and some fixes
	 *  	- the slider size has now getter and setter
	 *  1.7.1 r22
	 *  	- is selected to scrolable
	 *  	- new GraphicFunktions class
	 *  	-  new package order
	 *  1.7.2 r23
	 *  	- FileManagerDesktop and FileManagerAndroid are now together in FileManager
	 *  1.7.3 r24
	 *  	- FileManager writing works now correctly
	 *  1.7.4 r25
	 *  	- added Bounding methods from SpaceKays
	 *  1.7.5 r26
	 *  	- added Colidable
	 *  	- Bounding methods are now the new Colideables
	 *  1.7.6 r27
	 *  	- Triangle now has rotation variable
	 *  	- changed the Colidable shape names from RECTANGLE to SHAPE_RECTANGLE for example
	 *  1.7.7 r28
	 *  	- Colidable now have moving booleans
	 *  	- optimizations in Colidable
	 *  1.8.0 r29
	 *  	- Colidable now can colide with triangles with moveX() and moveY()
	 *  		- it will be optimized in the upcomming 1.8.x updates
	 *  1.9.0build1 r30 -> 1.9.0 r36
	 *  	- added new Expandable Button
	 *  	- fixed "Scrolbar" bugs.
	 *  	- added new OwnFont.class it replaces for me the BitmapFont class of libGDX
	 *  		- it uses a own font loading system(i know its a disatvantage)
	 *  			- format you need a .txt file
	 *  			- if you want to register a char write (charid)_x(xpos)_y(ypos)_w(width)_h(height) casesensitive and you need this order the x,y,w,h before every is not requiered;
	 *  			- optinal you can add:
	 *  				- _ay(yoffset) for an  y offset usefull for chars like g,y or p 
	 *  				- and _s(scale) to increase or deacreas the size of single chars
	 *  				- casesensitive but order is not important
	 *  			- the (...) stand for a value in float format so you can write comma (except the charid,x,y,w and h (but iam not 100%sure :D))  
	 *  			- a_ready line_ looks like this "103_x872_y42_w38_h57_ay-16" (except the "")
	 *  			- the font texture has to be a png format and must have the same name so you have font.txt and font.png		
	 *  		- you can change the color with setColor
	 *  		- you can add often used Colors in the cache to improve performance with addCacheColor(Color) (int the moment a max 3 cached colors)
	 *  		- you can change the global scale with setScale
	 *  		- you can get the bounds of o word or char with getWordBounds(String)
	 *  		- you can only paint a region of the font usefull if you only need some letters of it
	 *  		- and more... in the future i will try to improve Quality of Live and Performance 
	 *  	- added a new Text.class it replaces for me the GlyphLayout class of libGDX
	 *  		- it uses OwnFont is used by the button class
	 *  		- you can draw whole Strings with it
	 *  		- it scales with the scale of the font and his own scale (fontscale * textscale) use setScale(float scale)
	 *  		- or setFontScale(float Scale)
	 *  		- text can have multiple lines the xy position is always on the down left corner of the first line
	 *  		- text can have a maximum line width so the same goes for the Button(WIP)
	 *  	- Big changes for Button class (v1.4)
	 *  		- Button now uses OwnFont and the Text Methods instead of BitMapFont and GlyphLayout
	 *  		- so there is much rewrite
	 *  		- removed the variables name and color they`re only accesable over the get Methods
	 *  		- Button no longer has MouseMoveLastPos it now uses the GestureWrappers new variable GestureWrapper.last_mouse_pos
	 *  		- text can have multiple lines the xy position is always on the down left corner of the first line
	 *  	- added new "colorize" and "colorize2" methods in GraphicFunktions.class
	 *  	- GameStateManager (GSM) can now use SpriteCaches (still W.I.P.)
	 *  	- added Background methods to Button.class
	 *  	- added TextStatic.class and ButtonStatic.class(still W.I.P.)
	 *  		- this are classes who can less than their big brothers Text.class and Button.class 
	 *  		- you cant change the name of them and they have less features
	 *  		- ButtonStatic DONT have:
	 *  			- Custom Background(but will came back eventually)
	 *  			- id System
	 *  			- Colliding(but will came back in a other way)
	 *  			- ability to change name
	 *  			- old name
	 *  		- TextStatic DONT have
	 *  			- positioning mode(but will came back)
	 *  			- ability to change name
	 *  		- but they should be less performance hungry and i have a few plans with that	
	 *  		- ButtonStatic DONT extends Button same for TextStatic and Text
	 *  1.9.1 r37
	 *  	- added " to thick and normal font
	 *  	- fixed a font draw bug
	 *  	- fixed a button bug
	 *  1.9.2 r38
	 *  	- fixed the x char for normal font
	 *  	- - and " position is now correct
	 *  1.9.3 r39
	 *  	- button now automatically updates the background when you change the maxtextsize
	 *  	- getTotalBounds in Text.class method improved
	 *  	- getTotalBounds is now available over the Button.class
	 *  1.9.4 r40
	 *  	- (added) Text.class and OwnFont.class can now write bold and Button.class of course to
	 *  	- (added) when you have 2 OwnFonts with the same data then they share their cache
	 *  	- (added) Button class now have getRealBounds
	 *  	- (fixed) Text.class should now cut the words correct
	 *      - (temporary removed) TextStatic.class
	 *      - (temporary removed) ButtonStatic.class
	 *  1.9.5 r41
	 *  	- (fixed) a bug in Text.class where not everything was colored correctly
	 *  1.9.6 r42
	 *  	- (fixed) a colour bug in Button.class
	 *  	- (optimized) OwnFont.class cache System (i expect  bugs)
	 *  1.9.7 r43
	 *  	- (fixed) Text.class line_abs_y now working correct
	 *  	- (fixed/added) writeable is now working again but not completely
	 *  	- (changed) renamed and fixed last_mouse_pos to mouse_pos in GestureWrapper.class
	 *  	- (changed)inverted the the Y position of the mouse so the 0:0 point is in the down left corner
	 *  	- some small shit i forgot to write down here
	 *  1.9.8 r44
	 *  	- small stuff
	 *  1.9.9 r45
	 *  	- (added) write method in Button.class can now take whole strings
	 *  1.9.10 - 1.9.11 r47
	 *  	- bug fixes
	 *  	- (revertchange)keydown now gives a keycode instead of char
	 *  	- a new UI texture
	 *  	- changed the background texture of scrolbar
	 *  1.9.12 r48
	 *  	- added jNativeHook library keyDown support (WIP)
	 *  1.9.13 r49
	 *  	- added ColortoFloat and FloattoColor Methods in GraphicFunktions
	 *  	- (fixed) Scrolbar bug where it still be drawn when it should be not shown
	 *  1.9.14 r50
	 *  	- (fixed) an issue with the Button.setName methods and the Background
	 *  	- (added) method to convert a colorFloat to a Stringname in GraphicFunktions
	 *  	- (added) method in Scrolable.class isDragged
	 *  	- the name of a scrolbar is now always in the middle
	 *  1.9.15 r51
	 *  	- (fixed) bug
	 *  1.9.16 r52
	 *  	- (fixes) triangle coliding
	 *  1.9.17 r53
	 *  	- (changed) moveX and moveY in Colideable now need ArrayList`s instead of Arrays
	 *  1.10.0 pre1 r54 (November 18)
	 *  	- improved triangle colidings(K-API) more exact colliding but less lagy
	 *  	- added pushable option in colideable
	 *  	- added a scale and a textscale option for Scrolable
	 *  	- hangs can now hover again
	 *  	- added the texture loader system in com.karmorak.api.texture
	 *  	- added € char to font
	 *  	- fixed Q and q in font
	 *  	- , is now a little bit more down in font
	 *  	- GestureWrapper now doesn`t need a return of a boolean anymore
	 *  
	 */

	public static final String VERSION = "1.10.0 pre1";
	public static final short RELEASE = 54;
	public static ApplicationType type;
	
	SpriteBatch batch;
	Texture img;
	
	public static File file;
	public static final String PC_PATH = ("Documents//tPoM//config.yml");
	
	public static float time;
	public static float time2;
	
	private static ConsoleOutputCapturer cs;
	
	public static ArrayList<Integer> caches = new ArrayList<>();
	
	
	public static void init(ApplicationType type) {
		Main.type = type;
		cs = new ConsoleOutputCapturer();
		cs.start();
	}
	
	public static String getVersionString() {	
		return "v." + VERSION + " r." +RELEASE;	
	}
	
	public static void updateTime() {	
		time += Gdx.graphics.getDeltaTime();
		time2 += Gdx.graphics.getDeltaTime();
	}
	
	public static void dispose(String logpath) {
//		try {
//			GlobalScreen.unregisterNativeHook();
//		} catch (NativeHookException e) {
//			e.printStackTrace();
//		}
		if(logpath != null && logpath != "") {
			String capturedValue = cs.stop();
	        String[] lines = capturedValue.split("/n");
	        for(String line : lines) {
	        	FileManager.write(line, logpath);
	        }		
		}
	}
	
}