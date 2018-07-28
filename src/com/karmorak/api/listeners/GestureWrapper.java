package com.karmorak.api.listeners;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.karmorak.api.Main;
import com.karmorak.api.button.Button;

public class GestureWrapper implements GestureListener, InputProcessor  {
	
	
	public static com.karmorak.api.Vector2 mouse_pos = new com.karmorak.api.Vector2(0, 0);
	
	private static ArrayList<GestureAdapter> list;
	private static final float HEIGHT = Gdx.graphics.getHeight();
	
	public static boolean touch = false, touch_dragg= false;
	private static float lastX = 0;
	public static float speed = 0;
	
	public static int firstKey = -1, secondKey = -1;
	
	public GestureWrapper() {
		InputMultiplexer im = new InputMultiplexer();
		InputProcessor ip = this;
		GestureDetector gd = new GestureDetector(this);
			
		im.addProcessor(ip);
		im.addProcessor(gd);
		Gdx.input.setInputProcessor(im);	
		
		list = new ArrayList<>();
	}

	public static void registerAdapater(GestureAdapter gsa) {
		list.add(gsa);
	}
	
	
	public static void removeAdapater(GestureAdapter gsa) {
		list.remove(gsa);
	}
	
	@SuppressWarnings("rawtypes")
	protected static void execute(String method, Class[] signature, Object[] parameters) {
		for(GestureAdapter gs : list) {
			try {
				//if(method != "nativeKeyPressed" && method != "nativeKeyTyped" && method != "nativeKeyReleased")
				invokeMethod(method, signature, parameters, gs.getClass(), gs);
			} catch(NoSuchMethodException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object invokeMethod(String methodName, Class[] signature, Object[] parameters, Class targetClass, Object target) throws Exception {
        Method m = targetClass.getDeclaredMethod(methodName, signature);
   
        if (Modifier.isPrivate(m.getModifiers())) {
            m.setAccessible(true);
        }
   
        return m.invoke(target, parameters);
    }
	
    
    public static int getFirstKey() {
		return firstKey;
	}
    
    public static int getSecondKey() {
		return secondKey;
	}
	
	@Override
	public boolean keyDown(int keycode) {		
		if((keycode == Keys.S || keycode == Keys.W)) 
			if(firstKey == -1)
				firstKey = 1;
			else if (secondKey == -1) 
				secondKey = 1;
				
				
		if((keycode == Keys.A || keycode == Keys.D)) 
			if(firstKey == -1)
				firstKey = 0;
			else if (secondKey == -1) 
				secondKey = 0;
//		System.out.println("L: " +keycode);
		execute("keyDown", new Class[] {int.class}, new Object[]  {keycode});		
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		if((keycode == Keys.S || keycode == Keys.W)) 
			if(firstKey == 1) {
				firstKey = secondKey;
				secondKey = -1;
			} else if (secondKey == 1) 
				secondKey = -1;			
				
		if((keycode == Keys.A || keycode == Keys.D)) 
			if(firstKey == 0) {
				firstKey = secondKey;
				secondKey = -1;
			} else if (secondKey == 0) 
				secondKey = -1;	
		execute("keyUp", new Class[] {int.class}, new Object[]  {keycode});		
		return false;
	}
	@Override
	public boolean keyTyped(char character) {		
		execute("keyTyped", new Class[] {char.class}, new Object[]  {character});		
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float Y = HEIGHT - screenY;
		
		mouse_pos.setPosition(screenX, Y);
		
		if(Main.type == ApplicationType.Desktop) {		
			Button.touchDown(screenX,(int) Y, pointer, button);
			execute("touchDown", new Class[] {int.class, int.class, int.class, int.class}, new Object[]  {screenX, (int) Y, pointer, button});	
			touch = true;
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		float Y = HEIGHT - screenY;
		mouse_pos.setPosition(screenX, Y);
		
		Button.touchUp(screenX, screenY, pointer, button);
		execute("touchUp", new Class[] {int.class, int.class, int.class, int.class}, new Object[]  {screenX, (int) Y, pointer, button});		
		touch = false;
		touch_dragg = false;
		return true;
	}
	
	float cur_speed = 0;
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float Y = HEIGHT - screenY;
		
		cur_speed = ((screenX - lastX) / Main.time);
		
		if(Main.time2 >= 1f) {
			Main.time2 = 0;
			GestureWrapper.speed = cur_speed;
		}
		
		Main.time = 0;
		lastX = screenX;
		mouse_pos.setPosition(screenX, Y);
		
		Button.touchDragged(screenX,(int) Y, pointer);
		execute("touchDragged", new Class[] {int.class, int.class, int.class}, new Object[]  {screenX,(int) Y, pointer});		
		touch_dragg = true;
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		float Y = HEIGHT - screenY;
		mouse_pos.setPosition(screenX, Y);
		cur_speed = ((screenX - lastX) / Main.time);
		
		if(Main.time2 >= 1f) {

			Main.time2 = 0;
			GestureWrapper.speed = cur_speed;
		}
		
		Main.time = 0;
		lastX = screenX;	
		Button.mouseMoved(screenX,(int) Y);		
		execute("mouseMoved", new Class[] {int.class, int.class}, new Object[]  {screenX,(int) Y});	
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		execute("scrolled", new Class[] {int.class}, new Object[]  {amount});	
		return false;
	}
	
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {		
		if(Main.type == ApplicationType.Android) {
			float Y = HEIGHT - y;
			mouse_pos.setPosition(x, Y);
			
			Button.touchDown((int)x,(int) y, pointer, button);
			execute("touchDown", new Class[] {int.class, int.class, int.class, int.class}, new Object[]  {(int)x, (int)Y, pointer, button});	
			touch = true;
		}
		return false;
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		float Y = HEIGHT - y;
		mouse_pos.setPosition(x, Y);
		execute("tap", new Class[] {float.class, float.class, int.class, int.class}, new Object[]  {x, Y, count, button});	
		return false;
	}
	@Override
	public boolean longPress(float x, float y) {
		float Y = HEIGHT - y;
		mouse_pos.setPosition(x, Y);
		execute("longPress", new Class[] {float.class, float.class}, new Object[]  {x, Y});		
		return false;
	}
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		execute("fling", new Class[] {float.class, float.class, int.class}, new Object[]  {velocityX, velocityY, button});		
		return false;
	}
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		execute("pan", new Class[] {float.class, float.class, float.class, float.class}, new Object[]  {x, y, deltaX, deltaY});		
		return false;
	}
	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		execute("panStop", new Class[] {float.class, float.class, int.class, int.class}, new Object[]  {x, y, pointer, button});	
		return false;
	}
	@Override
	public boolean zoom(float initialDistance, float distance) {
		execute("zoom", new Class[] {float.class, float.class}, new Object[]  {initialDistance, distance});			
		return false;
	}
	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		execute("pinch", new Class[] {Vector2.class, Vector2.class, Vector2.class, Vector2.class}, new Object[]  {initialPointer1, initialPointer2, pointer1, pointer2});		
		return false;
	}
	@Override
	public void pinchStop() {
		execute("pinchStop", new Class[0], new Object[0]);				
	}
	
}
