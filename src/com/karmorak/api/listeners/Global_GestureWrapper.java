package com.karmorak.api.listeners;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import com.badlogic.gdx.Input.Keys;


/**
 * use this instead of GestureWrapper when you need the keyevents when program is minimized(KeyLogger)
 */
public class Global_GestureWrapper extends GestureWrapper implements NativeKeyListener {
	
	
	
	public Global_GestureWrapper() {
		super();	
		System.out.print("Loading jNativeHook-Library v2.1.0 ...");
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
   		logger.setLevel(Level.OFF);
   		logger.setUseParentHandlers(false);
   		
		try {
			GlobalScreen.registerNativeHook(); 
	   		GlobalScreen.addNativeKeyListener(this);		   		
	   		System.out.println("...jNativeHook Ready");
	   		//GlobalScreen.setEventDispatcher(new VoidDispatchService());
	   	} catch (NativeHookException ex) {
	        System.err.println("There was a problem registering the native hook.");
	        System.err.println(ex.getMessage());
	        //System.exit(1);
	    }
		
	}
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		execute("keyNativeDown", new Class[] {int.class}, new Object[]  {getGDXKey(e)});		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}
		
	protected static int getGDXKey(NativeKeyEvent nativeEvent) {	
		
		int keyLocation  = KeyEvent.KEY_LOCATION_UNKNOWN;
		switch (nativeEvent.getKeyLocation()) {
			case NativeKeyEvent.KEY_LOCATION_STANDARD:
				keyLocation = KeyEvent.KEY_LOCATION_STANDARD;
				break;

			case NativeKeyEvent.KEY_LOCATION_NUMPAD:
				keyLocation = KeyEvent.KEY_LOCATION_NUMPAD;
				break;

			case NativeKeyEvent.KEY_LOCATION_LEFT:
				keyLocation = KeyEvent.KEY_LOCATION_STANDARD;
				break;

			case NativeKeyEvent.KEY_LOCATION_RIGHT:
				keyLocation = KeyEvent.KEY_LOCATION_RIGHT;
				break;
		}

		int keyCode = Keys.UNKNOWN;
		switch (nativeEvent.getKeyCode()) {
			case NativeKeyEvent.VC_ESCAPE:
				keyCode = Keys.ESCAPE;
				break;

			// Begin Function Keys
			case NativeKeyEvent.VC_F1:
				keyCode = Keys.F1;
				break;

			case NativeKeyEvent.VC_F2:
				keyCode = Keys.F2;
				break;

			case NativeKeyEvent.VC_F3:
				keyCode = Keys.F3;
				break;

			case NativeKeyEvent.VC_F4:
				keyCode = Keys.F4;
				break;

			case NativeKeyEvent.VC_F5:
				keyCode = Keys.F5;
				break;

			case NativeKeyEvent.VC_F6:
				keyCode = Keys.F6;
				break;

			case NativeKeyEvent.VC_F7:
				keyCode = Keys.F7;
				break;

			case NativeKeyEvent.VC_F8:
				keyCode = Keys.F8;
				break;

			case NativeKeyEvent.VC_F9:
				keyCode = Keys.F9;
				break;

			case NativeKeyEvent.VC_F10:
				keyCode = Keys.F10;
				break;

			case NativeKeyEvent.VC_F11:
				keyCode = Keys.F11;
				break;

			case NativeKeyEvent.VC_F12:
				keyCode = Keys.F12;
				break;
			// End Function Keys


			// Begin Alphanumeric Zone
			case NativeKeyEvent.VC_BACKQUOTE:
				keyCode = Keys.GRAVE;
				break;

			case NativeKeyEvent.VC_1:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_1;
				else keyCode = Keys.NUM_1;
				break;

			case NativeKeyEvent.VC_2:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_2;
				else keyCode = Keys.NUM_2;
				break;

			case NativeKeyEvent.VC_3:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_3;
				else keyCode = Keys.NUM_3;
				break;

			case NativeKeyEvent.VC_4:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_4;
				else keyCode = Keys.NUM_4;
				break;

			case NativeKeyEvent.VC_5:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_5;
				else keyCode = Keys.NUM_5;
				break;

			case NativeKeyEvent.VC_6:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_6;
				else keyCode = Keys.NUM_6;
				break;

			case NativeKeyEvent.VC_7:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_7;
				else keyCode = Keys.NUM_7;
				break;

			case NativeKeyEvent.VC_8:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_8;
				else keyCode = Keys.NUM_8;
				break;

			case NativeKeyEvent.VC_9:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_9;
				else keyCode = Keys.NUM_9;
				break;

			case NativeKeyEvent.VC_0:
				if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD)keyCode = Keys.NUMPAD_0;
				else keyCode = Keys.NUM_0;
				break;


			case NativeKeyEvent.VC_MINUS:
				keyCode = Keys.MINUS;
				break;

			case NativeKeyEvent.VC_EQUALS:
				keyCode = Keys.EQUALS;
				break;

			case NativeKeyEvent.VC_BACKSPACE:
				keyCode = Keys.BACKSPACE;
				break;


			case NativeKeyEvent.VC_TAB:
				keyCode = Keys.TAB;
				break;

			case NativeKeyEvent.VC_CAPS_LOCK:
				keyCode = Keys.STAR;
				//TODO
				break;


			case NativeKeyEvent.VC_A:
				keyCode = Keys.A;
				break;

			case NativeKeyEvent.VC_B:
				keyCode = Keys.B;
				break;

			case NativeKeyEvent.VC_C:
				keyCode = Keys.C;
				break;

			case NativeKeyEvent.VC_D:
				keyCode = Keys.D;
				break;

			case NativeKeyEvent.VC_E:
				keyCode = Keys.E;
				break;

			case NativeKeyEvent.VC_F:
				keyCode = Keys.F;
				break;

			case NativeKeyEvent.VC_G:
				keyCode = Keys.G;
				break;

			case NativeKeyEvent.VC_H:
				keyCode = Keys.H;
				break;

			case NativeKeyEvent.VC_I:
				keyCode = Keys.I;
				break;

			case NativeKeyEvent.VC_J:
				keyCode = Keys.J;
				break;

			case NativeKeyEvent.VC_K:
				keyCode = Keys.K;
				break;

			case NativeKeyEvent.VC_L:
				keyCode = Keys.L;
				break;

			case NativeKeyEvent.VC_M:
				keyCode = Keys.M;
				break;

			case NativeKeyEvent.VC_N:
				keyCode = Keys.N;
				break;

			case NativeKeyEvent.VC_O:
				keyCode = Keys.O;
				break;

			case NativeKeyEvent.VC_P:
				keyCode = Keys.P;
				break;

			case NativeKeyEvent.VC_Q:
				keyCode = Keys.Q;
				break;

			case NativeKeyEvent.VC_R:
				keyCode = Keys.R;
				break;

			case NativeKeyEvent.VC_S:
				keyCode = Keys.S;
				break;

			case NativeKeyEvent.VC_T:
				keyCode = Keys.T;
				break;

			case NativeKeyEvent.VC_U:
				keyCode = Keys.U;
				break;

			case NativeKeyEvent.VC_V:
				keyCode = Keys.V;
				break;

			case NativeKeyEvent.VC_W:
				keyCode = Keys.W;
				break;

			case NativeKeyEvent.VC_X:
				keyCode = Keys.X;
				break;

			case NativeKeyEvent.VC_Y:
				keyCode = Keys.Y;
				break;

			case NativeKeyEvent.VC_Z:
				keyCode = Keys.Z;
				break;


			case NativeKeyEvent.VC_OPEN_BRACKET:
				keyCode = Keys.LEFT_BRACKET;
				break;

			case NativeKeyEvent.VC_CLOSE_BRACKET:
				keyCode = Keys.RIGHT_BRACKET;
				break;

			case NativeKeyEvent.VC_BACK_SLASH:
				keyCode = Keys.BACKSLASH;
				break;


			case NativeKeyEvent.VC_SEMICOLON:
				keyCode = Keys.SEMICOLON;
				break;

			case NativeKeyEvent.VC_QUOTE:
				keyCode = Keys.APOSTROPHE;
				//TODO
				break;

			case NativeKeyEvent.VC_ENTER:
				keyCode = Keys.ENTER;
				break;


			case NativeKeyEvent.VC_COMMA:
				keyCode = Keys.COMMA;
				break;

			case NativeKeyEvent.VC_PERIOD:
				keyCode = Keys.PERIOD;
				break;

			case NativeKeyEvent.VC_SLASH:
				keyCode = Keys.SLASH;
				break;

			case NativeKeyEvent.VC_SPACE:
				keyCode = Keys.SPACE;
				break;
			// End Alphanumeric Zone


			case NativeKeyEvent.VC_PRINTSCREEN:
				keyCode = Keys.UNKNOWN;
				//TODO
				break;

			case NativeKeyEvent.VC_SCROLL_LOCK:
				keyCode = Keys.UNKNOWN;
				//TODO
				break;

			case NativeKeyEvent.VC_PAUSE:
				keyCode = Keys.MEDIA_PLAY_PAUSE;
				break;


			// Begin Edit Key Zone
			case NativeKeyEvent.VC_INSERT:
				keyCode = Keys.INSERT;
				break;

			case NativeKeyEvent.VC_DELETE:
				keyCode = Keys.DEL;
				break;

			case NativeKeyEvent.VC_HOME:
				keyCode = Keys.HOME;
				break;

			case NativeKeyEvent.VC_END:
				keyCode = Keys.END;
				break;

			case NativeKeyEvent.VC_PAGE_UP:
				keyCode = Keys.PAGE_UP;
				break;

			case NativeKeyEvent.VC_PAGE_DOWN:
				keyCode = Keys.PAGE_DOWN;
				break;
			// End Edit Key Zone


			// Begin Cursor Key Zone
			case NativeKeyEvent.VC_UP:
				keyCode = Keys.UP;
				break;
			case NativeKeyEvent.VC_LEFT:
				keyCode = Keys.LEFT;
				break;
			case NativeKeyEvent.VC_CLEAR:
				keyCode = Keys.CLEAR;
				break;
			case NativeKeyEvent.VC_RIGHT:
				keyCode = Keys.RIGHT;
				break;
			case NativeKeyEvent.VC_DOWN:
				keyCode = Keys.DOWN;
				break;
			// End Cursor Key Zone


			// Begin Numeric Zone
			case NativeKeyEvent.VC_NUM_LOCK:
				keyCode = Keys.NUM;
				break;

			case NativeKeyEvent.VC_SEPARATOR:
				keyCode = Keys.BACKSLASH;
				//TODO
				break;
			// End Numeric Zone


			// Begin Modifier and Control Keys
			case NativeKeyEvent.VC_SHIFT:
				if (keyLocation == KeyEvent.KEY_LOCATION_RIGHT){
					keyCode = Keys.SHIFT_RIGHT;
				} else {
					keyCode = Keys.SHIFT_LEFT;
				}
				break;

			case NativeKeyEvent.VC_CONTROL:				
				if (keyLocation == KeyEvent.KEY_LOCATION_RIGHT){
					keyCode = Keys.CONTROL_RIGHT;
				} else {
					keyCode = Keys.CONTROL_LEFT;
				}
				break;

			case NativeKeyEvent.VC_ALT:
				if (keyLocation == KeyEvent.KEY_LOCATION_RIGHT){
					keyCode = Keys.ALT_RIGHT;
				} else {
					keyCode = Keys.ALT_LEFT;
				}
				break;

			case NativeKeyEvent.VC_META:
				keyCode = Keys.UNKNOWN;
				//TODO
				break;

			case NativeKeyEvent.VC_CONTEXT_MENU:
				keyCode =Keys.UNKNOWN;
				//TODO
				break;
			// End Modifier and Control Keys


			/* Begin Media Control Keys
			case NativeKeyEvent.VC_POWER:
			case NativeKeyEvent.VC_SLEEP:
			case NativeKeyEvent.VC_WAKE:
			case NativeKeyEvent.VC_MEDIA_PLAY:
			case NativeKeyEvent.VC_MEDIA_STOP:
			case NativeKeyEvent.VC_MEDIA_PREVIOUS:
			case NativeKeyEvent.VC_MEDIA_NEXT:
			case NativeKeyEvent.VC_MEDIA_SELECT:
			case NativeKeyEvent.VC_MEDIA_EJECT:
			case NativeKeyEvent.VC_VOLUME_MUTE:
			case NativeKeyEvent.VC_VOLUME_UP:
			case NativeKeyEvent.VC_VOLUME_DOWN:
			case NativeKeyEvent.VC_APP_MAIL:
			case NativeKeyEvent.VC_APP_CALCULATOR:
			case NativeKeyEvent.VC_APP_MUSIC:
			case NativeKeyEvent.VC_APP_PICTURES:
			case NativeKeyEvent.VC_BROWSER_SEARCH:
			case NativeKeyEvent.VC_BROWSER_HOME:
			case NativeKeyEvent.VC_BROWSER_BACK:
			case NativeKeyEvent.VC_BROWSER_FORWARD:
			case NativeKeyEvent.VC_BROWSER_STOP:
			case NativeKeyEvent.VC_BROWSER_REFRESH:
			case NativeKeyEvent.VC_BROWSER_FAVORITES:
			// End Media Control Keys */


			// Begin Japanese Language Keys
//			case NativeKeyEvent.VC_KATAKANA:
//				keyCode = Keys.KATAKANA;
//				break;
//
//			case NativeKeyEvent.VC_UNDERSCORE:
//				keyCode = Keys.UNDERSCORE;
//				break;

			//case VC_FURIGANA:

//			case NativeKeyEvent.VC_KANJI:
//				keyCode = Keys.KANJI;
//				break;
//
//			case NativeKeyEvent.VC_HIRAGANA:
//				keyCode = Keys.HIRAGANA;
//				break;

			//case VC_YEN:
			// End Japanese Language Keys


			// Begin Sun keyboards
//			case NativeKeyEvent.VC_SUN_HELP:
//				keyCode = Keys.HELP;
//				break;
//
//			case NativeKeyEvent.VC_SUN_STOP:
//				keyCode = Keys.STOP;
//				break;

			//case VC_SUN_FRONT:

			//case VC_SUN_OPEN:

//			case NativeKeyEvent.VC_SUN_PROPS:
//				keyCode = Keys.PROPS;
//				break;
//
//			case NativeKeyEvent.VC_SUN_FIND:
//				keyCode = Keys.FIND;
//				break;
//
//			case NativeKeyEvent.VC_SUN_AGAIN:
//				keyCode = Keys.AGAIN;
//				break;

			//case NativeKeyEvent.VC_SUN_INSERT:

//			case NativeKeyEvent.VC_SUN_COPY:
//				keyCode = Keys.COPY;
//				break;
//
//			case NativeKeyEvent.VC_SUN_CUT:
//				keyCode = Keys.CUT;
//				break;
			// End Sun keyboards
		}
				
		return keyCode;
	}

}
