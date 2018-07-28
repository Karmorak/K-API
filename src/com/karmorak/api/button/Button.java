//v 1.4-build5
package com.karmorak.api.button;


import java.util.ArrayList;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Vector2;
import com.karmorak.api.font.OwnFont;
import com.karmorak.api.font.Text;
import com.karmorak.api.funktions.GraphicFunktions;
import com.karmorak.api.listeners.GestureWrapper;

public class Button implements Comparable<Button>{

	

	protected static int max_id;
	public static int hovered_Button = -1;
	public static int selected_Button = -1;
	private static boolean first_init = false;
	private static final String FONT_PATH = "assets//fonts//font";
	private static final OwnFont def_font = new OwnFont(FileType.Classpath, FONT_PATH, Color.WHITE);
	private static final Texture def_background = new Texture(Gdx.files.internal("assets//button_background.png"));
	
	protected static ArrayList<Button> list;
	protected static ArrayList<Button> scrols;
	protected static ArrayList<Expandable> exp_list = new ArrayList<>();
	
	private static ArrayList<char[]> list2;
	private static char[] alphaandnumber; // 0
	private static char[] alphabetical; // 1
	private static char[] number; //2	
	private static char[] sonder; // 3
	private static char[] AlphaSonder; // 4
	private static char[] all; // 5
	
	
	protected boolean show;
	protected boolean can_hover = true;
	protected boolean can_select = true;	
	
	protected Text text;
	protected OwnFont font;
	protected int ID = 0;
	protected Vector2 pos;
	protected String[] old_name;
	
	protected Color originColor;
	protected Color hoverColor;
	protected Color selectColor;	
	protected String hoverText;
	protected String selectText;
	
	protected Background background;
	
	private ArrayList<Hang> hangs;
	
	public class Background {
		
		private Texture texture;
		protected boolean bg_show;
		private Vector2 bg_pos;
		private Vector2 bounds;
		private Vector2 bg_offset;
		private boolean bg_unrelative;
		
		public Background() {
			texture = def_background;
			bg_pos = new Vector2(0, 0);
			bounds = new Vector2(text.getRealWidth(), text.getTotalBounds().getHeight());			
			bg_offset = new Vector2(2, 2);
		}
		
		public void setColor(Color c) {
			texture = GraphicFunktions.colorize(def_background, c, 1f);
		}
		
		public void setPosition(Vector2 pos) {
			bg_pos = new Vector2(pos.getX(), pos.getY());
		}
		
		public void show(boolean bool) {
			bg_show = bool;
		}	
		public void setOffset(float offset) {
			bg_offset.setPosition(offset, offset);
		}
		
		public void setXOffset(float offset) {
			bg_offset.setX(offset);
		}
		public void setYOffset(float offset) {
			bg_offset.setY(offset);
		}
		
		public void setPositioningMode(boolean unrelative) {
			bg_unrelative = unrelative;
		}
		
		void bounds() {
			bounds = new Vector2(text.getRealWidth(), text.getTotalBounds().getHeight());
		}
		
		public void draw(SpriteBatch batch) {
			if(show)
				if(!bg_unrelative)						
					batch.draw(texture, text.getTotalPosition().getX() + bg_pos.getX()-bg_offset.getX() + text.getOffsetX(),text.getTotalPosition().getY() + bg_pos.getY()-bg_offset.getY(), bounds.getWidth() + bg_offset.getX()*2, bounds.getHeight() + bg_offset.getY()*2);		
				else
					batch.draw(texture, bg_pos.getX()-bg_offset.getX(), bg_pos.getY()-bg_offset.getY(), bounds.getWidth() + bg_offset.getX()*2, bounds.getHeight() + bg_offset.getY()*2);
		}
		
	}
	
	private void init(OwnFont font, String[] name) {
		if(!first_init) {
			list = new ArrayList<>();
			list2 = new ArrayList<>();
			
			def_font.addCacheColor(Color.RED, false);
			def_font.addCacheColor(Color.CYAN, false);
			
			alphaandnumber = "0123456789abcdefghijklmnopqrstuvwxyz���".toCharArray();
			alphabetical = "abcdefghijklmnopqrstuvwxyz���".toCharArray();
			number = "0123456789".toCharArray();
			sonder = "<>|^�`�+#-'*~:.;,_!�$%&/({[]}\\)\"=?".toCharArray();
			AlphaSonder = "abcdefghijklmnopqrstuvwxyz���<>|^�`�+#-'*~:.;,_!�$%&/({[]}\\\\)\\\"=?".toCharArray();
			all = "abcdefghijklmnopqrstuvwxyz���0123456789<>|^�`�+#-'*~:.;,_!�$%&/({[]}\\)\"=?".toCharArray();
			
			scrols = new ArrayList<>();
			
			list2.add(alphaandnumber);
			list2.add(alphabetical);
			list2.add(number);
			list2.add(sonder);
			list2.add(AlphaSonder);
			list2.add(all);
			
			first_init = true;
		}		
		
//		scale = 1f;		
		hoverColor = Color.RED;
		selectColor = Color.CYAN;
		originColor = Color.WHITE;
		hoverText = "";
		selectText = "";
		hangs = new ArrayList<Hang>();
		list.add(this);
		this.font = font;
		text = new Text(this.font, name);
		//text.setColor(Color.WHITE);
		old_name = name;		
		show = true;
		if(ID == 0) {
			ID = max_id;		
			max_id++;
		}
	}

	public Button (String name, Vector2 pos) {
		init(def_font, new String[]{name});
		if(pos != null) {
			this.setPosition(pos); 
		} else {
			this.setPosition(0, Gdx.graphics.getHeight());
		}	
	}
	
	public Button (OwnFont font, String name, Vector2 pos) {
		init(font, new String[]{name});
		if(pos != null) {
			this.setPosition(pos); 
		} else {
			this.setPosition(0, Gdx.graphics.getHeight());
		}	
	}
	
	public Button (String name) {		
		init(def_font, new String[]{name});
		this.pos = new Vector2(0, Gdx.graphics.getHeight() - getheight());	
	}
	
	public Button (OwnFont font, String name) {
		init(font, new String[]{name});
		this.pos = new Vector2(0, Gdx.graphics.getHeight() - getheight());	
	}
	
	public Button (String[] name, Vector2 pos) {
		init(def_font, name);
		if(pos != null) {
			this.setPosition(pos); 
		} else {
			this.setPosition(0, Gdx.graphics.getHeight());
		}	
	}
	
	public Button (OwnFont font, String[] name, Vector2 pos) {
		init(font, name);
		if(pos != null) {
			this.setPosition(pos); 
		} else {
			this.setPosition(0, Gdx.graphics.getHeight());
		}	
	}
	
	public Button (String[] name) {		
		init(def_font, name);
		this.pos = new Vector2(0, Gdx.graphics.getHeight() - getheight());	
	}
	
	public Button (OwnFont font, String[] name) {
		init(font, name);
		this.pos = new Vector2(0, Gdx.graphics.getHeight() - getheight());	
	}
	

	
	protected Button () {
	}
	
	public void setWritable(int hang,int maxChars, int writemode) {
		Hang h = hangs.get(hang);	
		Writable w = new Writable(h, maxChars);
		w.setSelectColor(new Color(0, 1f, 1f, 1f));
		
		hangs.set(hang, w);
	}
	
	public void setHoverable(boolean bool) {
		can_hover = bool;
	}
	public void setSelectable(boolean bool) {
		can_select = bool;
	}
	
	public void setInteractable(boolean bool) {
		can_hover = bool;
		can_select = bool;
	}
	
	public boolean isHoverable() {
		return can_hover;
	}
	public boolean isSelectable() {
		return can_select;
	}
	
	public void dispose() {
		pos = null;
		font.dispose();
		font = null;
		ID = 0;
		removeButton(this);
		cleanIDs();		
		hangs.clear();
	}
	
	public void setlineYabs(float abs) {
		text.setlineYabs(abs);
	}
	
	public float maxX() {
		return pos.getX() + getwidth();
	}
	
	public float maxY() {
		return pos.getY()+getheight();
	}
	
	public void show(boolean bool) {
		show = bool;
	}
	public boolean getshow() {
		return show;
	}
	
	public void initBackground() {
		background = new Background();
	}
	
	public Background getBackground() {
		return background;
	}
	
	public Button setMaxTextWidth(float width) {
		text.setMaxWidth(width);
		if(getBackground()!= null)
			getBackground().bounds();
		return this;
	}
	
	public Button setPositioningMode(int mode) {
		text.pos_mode = mode;
		return this;
	}
	
	public Button setPosition(Vector2 pos) {
		this.pos = new Vector2(pos.getX(), pos.getY());
		this.text.setPosition(pos.getX(), pos.getY());
		if(hangs != null)
		for(Hang h : hangs) {
			h.updatePosition();
		}

		return this;
	}
	
	public Button setPosition(float x, float y) {
		this.pos = new Vector2(x, y);	
		text.setPosition(x, y);
		if(hangs != null)
		for(Hang h : hangs) {
			h.updatePosition();
		}
		return this;
	}
	
	public void setPosition(int hang, Vector2 pos) {
		hangs.get(hang).setPosition(new Vector2(pos.getX(), pos.getY()));
	}
	
	public void setPosition(int hang, float x, float y) {
		hangs.get(hang).setPosition(x, y);
	}
	
	public Vector2 getPosition() {		
		return pos;		
	}
	public Vector2 getTotalPosition() {
		return text.getTotalPosition();		
	}
	
	public Vector2 getPosition(int hang) {
		return hangs.get(hang).getPosition();		
	}
	
	
	public Button setHangLeft(int i) {
		Hang b = hangs.get(i);
		b.setPosition(-b.getwidth(), 0);	
		return this;
	}
	
	public Button setHangRight(int i) {
		Hang b = hangs.get(i);
		b.setPosition(getwidth(), 0);	
		return this;
	}
	
	public void addFontCacheColor(Color c, boolean thick) {
		this.font.addCacheColor(c, thick);
	}
	
	public Button setMiddle() {
		setPosition((Gdx.graphics.getWidth() - getwidth()) * 0.5f, (Gdx.graphics.getHeight() - getheight()) * 0.5f);
		return this;
	}
	
	public Button getHang(int i) {
		return hangs.get(i);
	}

	
	public Button setName(String name) {
		text.setName(name);
		return this;
	}	
	public Button setName(String[] name) {
		text.setName(name);
		return this;
	}	
	
	public Button setName(int index, String name) {
		text.setName(index, name);
		return this;
	}	
	
	public String getName() {		
		return this.text.getName();
	}
	public String[] getNames() {		
		return this.text.getNames();
	}
	public String getName(int index) {		
		return this.text.getName(index);
	}
	
	public Button setFont(OwnFont font) {
		this.font = font;
		return this;
	}
	
	public Button setColor(Color color) {
		text.setColor(color);
		originColor = color;
		return this;
	}
	public Button setColor(int i, Color color) {
		hangs.get(i).setColor(color);
		originColor = color;
		return this;
	}
//	public Button setColor(Color color, boolean changeorigin) {
//		text.setColor(color);
//		return this;
//	}
//	public Button setColor(int i, Color color, boolean changeorigin) {
//		hangs.get(i).setColor(color);
//		return this;
//	}
	
	public Color getColor() {
		return text.getColor();
	}
	
	public Button setHoverColor(Color color) {
		hoverColor = color;
		return this;
	}
	
	public Button setSelectColor(Color color) {
		selectColor = color;
		return this;
	}	

	public Button setAllColor(Color color) {
		for(int i = 0; i < hangs.size(); i++) {
			setColor(i, color);
		}
		setColor(color);
		return this;
	}
	
	public void setHoverText(String text) {
		hoverText = text;
	}
	
	public void setSelectText(String text) {
		selectText = text;
	}

	public Vector2 getRealBounds() {
		return new Vector2(text.getRealWidth(), text.getTotalBounds().getHeight());
	}
	
	public Vector2 getTotalBounds() {
		return text.getTotalBounds();
	}
	
	public float getwidth() {			
		return text.getWidth();
	}
	
	public float getwidth(int index) {			
		return text.getWidth(index);	
	}
	
	public float getheight() {		
		return text.getHeight();
	}
	
	public float getheight(int index) {		
		return text.getHeight(index);
	}
	
	public static Button getButton(int ID) {
		for(int i = 0; i < list.size(); i++) {
			Button b = list.get(i);
			if(b.getID() == ID) {
				return b;
			}
		}
		return null;
	}
	
	public int getID() {
		return ID;
	}

	
	public static int getID(Button b) {
		return b.ID;
	}
	
	private void setID(int i) {
		if(i == 0 || i == -1) {
			ID = max_id;		
			max_id++;
		} else {
			ID = i;
		}

	}
	
	public String getoldName() {
		return old_name[0];
	}
	
	public String getoldName(int index) {
		return old_name[index];
	}
	
	public String getText(int hang) {
		return getHang(hang).getName().replace("_", "");
	}
	
	public Button addHang(String name) {
		Hang c= new Hang(font, name, getID());
		hangs.add(c);
		c.setPosition(-15, 0);
		return c;
	}
	
	public Button addHang(String name, Color color) {
		Hang c = new Hang(this.font, name, getID());
		hangs.add(c);
		c.setPosition(-15, 0);	
		return c;
	}
	
	public void addHang(String name, Vector2 pos) {
		addHang(name);
		setPosition(hangs.size()-1, pos);
	}
	
	public void addHang(String name, Vector2 pos, Color color) {
		addHang(name);
		setPosition(hangs.size()-1, pos);
		setColor(hangs.size()-1, color);
	}
	
	public void addHang(String name, float x, float y) {
		addHang(name);
		setPosition(hangs.size()-1, new Vector2(x, y));
	}
	
	public void addHang(String name, float x, float y, Color color) {
		addHang(name);
		setPosition(hangs.size()-1, new Vector2(x, y));
		setColor(hangs.size()-1, color);
	}
	
	public static void cleanIDs() {		
		for(int i = 0; i < list.size(); i++) {
			Button b = list.get(i);
			b.setID(i+1);		
		}		
	}
	
	public void setScale(float scale) {
		text.setScale(scale);
	}
	
	public float getScale() {
		return text.getScale();
	}
	
	public void setFontScale(float scale) {
		font.setScale(scale);
	}
	public float getFontScale() {
		return font.getScale();
	}
	
	@SuppressWarnings("unchecked")
	private static void removeButton(Button b) {
		ArrayList<Button> c = new ArrayList<Button>();		
		for(int i = 0; i < list.size(); i++) {
			Button b_2 = list.get(i);
			if(!b_2.equals(b)) {
				c.add(b_2);
			}	
		}
		list.clear();
		list = (ArrayList<Button>) c.clone();
		c.clear();	
	}
	
	public boolean isSelected() {
		if(!isSelectable() || this instanceof Expandable) {
			Vector2 last_mouse_pos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			int mouse_pos_x = (int) last_mouse_pos.getX();
			int mouse_pos_y = (int) (Gdx.graphics.getHeight() - last_mouse_pos.getY());
			
			float button_pos_x = getPosition().getX();
			float button_pos_y = getPosition().getY();
			
			
			if(mouse_pos_x > button_pos_x &&
					mouse_pos_x < button_pos_x + getwidth() &&
						mouse_pos_y < button_pos_y + getheight() &&
							mouse_pos_y > button_pos_y) {
				return true;	
			} else {
				return false;
			}
		} else {
			if(selected_Button == getID()) {
				return true;
			} else {
				return false;
			}		
		}
	}
	
	public static int getHoveredButton(ArrayList<Button> buttons) {		
		if(buttons != null && buttons.size() > 0) {
			for (Button c : buttons) {
				if(c.show && c.can_hover) {
					Vector2 last_mouse_pos = GestureWrapper.mouse_pos;
					
					int mouse_x = (int) last_mouse_pos.getX();
					int mouse_y = (int) last_mouse_pos.getY();			
					
					
					float button_x = 0;		
					float button_y = 0;
					
					float width = 0;
					float height = 0;
					
					if(c instanceof Hang) {
						button_y = ((Hang)c).getRelPosition().getY();
						button_x = ((Hang)c).getRelPosition().getX();
						width = c.getwidth();
						height = c.getheight();
					} else if(c instanceof Scrolable) {
						button_x = ((Scrolable) c).getSliderPos().getX();
						button_y = ((Scrolable) c).getSliderPos().getY();
						width = ((Scrolable) c).getSliderWidth();
						height = ((Scrolable) c).getSliderHeight();
					} else {
						button_y = c.getPosition().getY();
						button_x = c.getPosition().getX();
						width = c.getwidth();
						height = c.getheight();
					}
					
					if(mouse_x > button_x &&
							mouse_x < button_x + width &&
								mouse_y > button_y &&
									mouse_y < button_y + height) {
						hovered_Button = c.getID();
						return c.getID();
					}		
				}
			}
		}
		return -1;		
	}
	

	protected void takeConfig(Button h) {
		list.add(h.getID(), this);
		this.font = h.font;
		old_name = h.getNames();	
		text = h.text;
		show = true;
		pos = h.getPosition();
		text.setColor(h.getColor());
		ID = h.getID();
//		scale = h.scale;
		selectColor = h.selectColor;
		hoverColor = h.hoverColor;
		hoverText = h.hoverText;
		originColor = h.originColor;
		selectText = h.selectText;
	}
	
	protected void setDefconfig(int id) {
		font = def_font;
		old_name = new String[] {"default"};	
		
		setID(id);
		list.add(ID, this);
//		scale = 1f;
		show = true;
		pos = new Vector2(0,0);		
		text = new Text(font, "default");
		
		setMiddle();	
	
		text.setColor(Color.WHITE);
		selectColor = Color.CYAN;
		hoverColor = Color.RED;
		originColor = Color.WHITE;
		hoverText = "";
		selectText = "";
	}
	
	
	public void draw(SpriteBatch batch) {	
		
		if(show) {			
			if(pos.getX() + getwidth() <= Gdx.graphics.getWidth() && pos.getX() >= 0 && pos.getY()+getheight() >= 0 && pos.getY() + getheight() <= Gdx.graphics.getHeight()) {				
				if(hovered_Button == this.getID() && can_hover)
					text.setColor(hoverColor);			
				else if(selected_Button == this.getID() && can_select)
					text.setColor(selectColor);
				else
					text.setColor(originColor);			
				
				if(background != null && background.bg_show)
					background.draw(batch);
				
				text.draw(batch);				
			}	
		
			
			if(hangs != null)
				for(Hang h : hangs) {
					h.draw(batch);
				}
		}
	}
	
	public static Vector2 getBoundsfromString(BitmapFont font, String str) {
		GlyphLayout gl = new GlyphLayout(font, str);
		return new Vector2(gl.width, gl.height);		
	}
	public static BitmapFont copyFont(BitmapFont font) {
		return new BitmapFont(font.getData().getFontFile());
	}
	
	public void setThickFont(boolean bool) {
		text.setThickFont(bool);
	}

	public void setoldName(String string) {
		this.old_name[0] = string;		
	}
	public void setoldName(String[] string) {
		this.old_name = string;		
	}	
	public void setoldName(int index, String string) {
		this.old_name[index] = string;		
	}
	
	public void write(int hang, char ch) {
		Writable w = (Writable) hangs.get(hang);
		
		String[] names = w.getNames();
		
		for(int i = 0; i < names.length; i++) {
			for(int i2 = 0; i2 < names[i].length(); i2++) {
				char c = names[i].charAt(i2);
				if(c == '_') {
					names[i] = names[i].replaceFirst("_", "" +ch);
					w.setName(i, names[i]);
					i = names.length;
					break;
				}			
			}		
		}
	}
	
	public void write(int hang, String ch) {
		Writable w = (Writable) hangs.get(hang);
		
		String[] names = w.getNames();
		int count = 0;
		
		for(int i = 0; i < names.length; i++) {
			for(int i2 = 0; i2 < names[i].length(); i2++) {
				char c = names[i].charAt(i2);
				if(c == '_') {
					names[i] = names[i].replaceFirst("_", "" +ch.charAt(count));
					count++;
					if(count == ch.length()) {
						w.setName(i, names[i]);
						i = names.length;
						break;
					}
				}			
			}		
		}
	}
	
	private static int dragg_button = -1;

	public static boolean touchDown(int screenX, int screenY, int pointer, int button) {

		int var1 = getHoveredButton(list);
		if(var1 != -1) {
			Button b = Button.getButton(var1);
			if(!(b instanceof Expandable)) {
				hovered_Button = b.getID();
				selected_Button = b.getID();
			} else {						
				if(selected_Button == b.getID()) {
					selected_Button = 0;							
				} else {
					selected_Button = b.getID();
				}
			}
		} else {
			hovered_Button = -1;
			selected_Button = -1;
		}
		
		for(Button b : scrols) {
			Scrolable c = (Scrolable)b;		
			int mouse_pos_y = (int) screenY;
			int mouse_pos_x = (int) screenX;
			
			float button_pos_y = c.getSliderPos().getY();
			float button_pos_x = c.getSliderPos().getX();	
			
			float buttonminy = (button_pos_y + c.getBGheight());			

			if(mouse_pos_x > button_pos_x &&
					mouse_pos_x < button_pos_x + c.getSliderWidth() &&
						mouse_pos_y > button_pos_y &&
							mouse_pos_y < buttonminy) {
				dragg_button = c.getID();					
			}

		}		
		Expandable.trigger();
		
		return false;
	}

	
	public static boolean mouseMoved(int screenX, int screenY) {
		lastX = screenX;
		lastY = screenY;
		int var1 = getHoveredButton(list);
		
		hovered_Button = var1;
		
		return false;
	}
	
	private static float lastX;
	private static float lastY;
	
	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		float speed_x = Math.abs((lastX - screenX));
		float speed_y = Math.abs((lastY - screenY));		
		if(dragg_button >= 0) {			
			Scrolable c = (Scrolable) getButton(dragg_button);
			if(c.IS_VERTICAL) {				
				if(lastY > screenY) {
					if(screenY < c.getSliderPos().getY() + c.getSliderHeight())
						if(c.getSliderPos().getY() > c.getBGPos().getY())
							c.getSliderPos().setY(c.getSliderPos().getY() - speed_y);	
				} else {
					if(screenY > c.getSliderPos().getY())
						if(c.getSliderPos().getY() +c.getSliderHeight() <= c.getBGPos().getY() + c.getBGheight())
							c.getSliderPos().setY(c.getSliderPos().getY() + speed_y);	
				}	
				
				if(c.getSliderPos().getY() < c.getBGPos().getY()) { 
					c.getSliderPos().setY(c.getBGPos().getY());
				} else if(c.getSliderPos().getY() + c.getSliderHeight() > c.getBGPos().getY() + c.getBGheight()) {
					c.getSliderPos().setY(c.getBGPos().getY() + c.getBGheight() - c.getSliderHeight());
				}
				
				float var1 = (c.getSliderPos().getY() - c.getBGPos().getY()) / (c.getBGheight() - c.getSliderHeight());			
				float var2 = (int) (var1 * 100);
				if(var2 < 0) var2 = 0f;
				
				c.setValue(var2*0.01f);
				
				if(!c.dontchangeName) {
					int var3 = (int) var2;			
					String name = "" + var3;
					
					if(name.length() == 1) {
						name = "0.0" + name;
					}else if(name.length() == 2) {
						name = "0." + name;
					} else if(name.length() == 3){
						String[] name2 = name.split("", 2);
						name = name2[0] + "." + name2[1];
					}
					c.setName(name);
				}	
				lastY = screenY;
			} else {
				if(lastX > screenX) {
					if(screenX < c.getSliderPos().getX() + c.getSliderWidth())
						if(c.getSliderPos().getX() > c.getBGPos().getX())
							c.getSliderPos().setX(c.getSliderPos().getX() - speed_x);	
				} else {
					if(screenX > c.getSliderPos().getX())
						if(c.getSliderPos().getX() + c.getSliderWidth() <= c.getBGPos().getX() + c.getBGwidth())
							c.getSliderPos().setX(c.getSliderPos().getX() + speed_x);	
				}
				
				if(c.getSliderPos().getX() + c.getSliderWidth() < c.getBGPos().getX()) { 
					c.getSliderPos().setX(c.getBGPos().getX());
				} else if(c.getSliderPos().getX() + c.getSliderWidth() > c.getBGPos().getX() + c.getBGwidth()) {
					c.getSliderPos().setX(c.getBGPos().getX() + c.getBGwidth() - c.getSliderWidth());
				}
				
				
				float var1 = (c.getSliderPos().getX() - c.getBGPos().getX() + (c.getSliderWidth() * 0.5f)) / (c.getBGwidth() - (c.getSliderWidth()));			
				float var2 = (int) ((var1 * 100)-(2*(240/c.getBGwidth())));
				if(var2 < 0) var2 = 0f;
				
				c.setValue(var2*0.01f);
				
				if(!c.dontchangeName) {
					int var3 = (int) var2;			
					String name = "" + var3;
					
					if(name.length() == 1) {
						name = "0.0" + name;
					}else if(name.length() == 2) {
						name = "0." + name;
					} else if(name.length() == 3){
						String[] name2 = name.split("", 2);
						name = name2[0] + "." + name2[1];
					}
					c.setName(name);
				}	
				lastX = screenX;
			}
			
	
		}
		
		return false;
	}
	
	public static boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(dragg_button >= 0 ) {			
			Scrolable c = (Scrolable) getButton(dragg_button);
			if(c.IS_VERTICAL) {
				if(c.getSliderPos().getY() < c.getBGPos().getY()) { 
					c.getSliderPos().setY(c.getBGPos().getY());
					if(!c.dontchangeName)
						c.setName("0.0");
				} else if(c.getSliderPos().getY() + c.getSliderHeight() > c.getBGPos().getY() + c.getBGheight()) {
					c.getSliderPos().setY(c.getBGPos().getY() + c.getBGheight() - c.getSliderHeight());
					if(!c.dontchangeName)
						c.setName("1.0");
				}
			} else {
				if(c.getSliderPos().getX() < c.getBGPos().getX()) { 
					c.getSliderPos().setX(c.getBGPos().getX());
					if(!c.dontchangeName)
						c.setName("0.0");
				} else if(c.getSliderPos().getX() + c.getSliderWidth() > c.getBGPos().getX() + c.getBGwidth()) {
					c.getSliderPos().setX(c.getBGPos().getX() + c.getBGwidth() - c.getSliderWidth());
					if(!c.dontchangeName)
						c.setName("1.0");
				}
			}
			dragg_button = -1;	
		}
			
		return false;		
	}
	
	@Override
	public int compareTo(Button o) {
		if (this.getID()<o.getID()) return -1;
		else if (this.getID()>o.getID()) return 1;
		else return 0;
	}



}