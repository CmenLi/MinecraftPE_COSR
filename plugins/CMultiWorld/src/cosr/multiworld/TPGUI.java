package cosr.multiworld;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class TPGUI {
	
	private Main plugin;
	
	public TPGUI() {
		plugin = Main.getInstance();
	}
	
	public void showMapsWindow(Player p) {
		Config worlds = Main.getWorldsConfig();
		FormWindowSimple window = new FormWindowSimple("�п�ܱz�Q�ǰe���@��", "");
		Map<Integer, Level> levels = plugin.getServer().getLevels();
		String title = "";
		for(Level level : levels.values()) {
			if(worlds != null) {
				title = worlds.getString(level.getFolderName(), level.getFolderName());
				window.addButton(new ElementButton(title + TextFormat.DARK_GRAY + " (" + level.getName() + ")"));
			}
		}
		window.addButton(new ElementButton("�s�W�a��"));
		
		p.showFormWindow(window);
	}
	
	public void showNewLevelWindow(Player p) {
		FormWindowCustom window = new FormWindowCustom("�s�W�a��");
		
		window.addElement(new ElementInput("�п�J�a���ɦW"));
		window.addElement(new ElementInput("�п�J�a�ϼ��D"));
		
		ElementDropdown dropList = new ElementDropdown("�п�ܦa������");
		dropList.addOption("survival", true);
		dropList.addOption("flat");
		dropList.addOption("nether");
		
		window.addElement(dropList);
		
		p.showFormWindow(window);
	}
	
	public void levelManageWindow(Player p, String levelName) {
		FormWindowSimple window = new FormWindowSimple("�޲z�a��" + levelName, "");
		window.addButton(new ElementButton("�ǰe�� " + levelName));
		window.addButton(new ElementButton("���� " + levelName));
		window.addButton(new ElementButton("�s�� " + levelName));
		window.addButton(new ElementButton("��^�ܦa�ϦC��"));
		
		p.showFormWindow(window);
	}
	
	public void LevelEditWindow(Player p, String levelName) {
		FormWindowCustom window = new FormWindowCustom("�s��a��-" + levelName);
		window.addElement(new ElementInput("�п�J�a�ϼ��D(�d�ūh�����)", TextFormat.ITALIC + "�a�ϼ��D�W", Main.getWorldsConfig().getString(levelName, levelName)));
		
		p.showFormWindow(window);
	}
	
	public void sureToDelWindow(Player p, String levelName) {
		FormWindowModal window = new FormWindowModal("�T�w�R���Ӧa��" + levelName + "��?", 
									(TextFormat.BOLD + (TextFormat.RED + "!")) + TextFormat.RESET + " �@���R����N�L�k�_��", "�T�w", "����");
		p.showFormWindow(window);
	}
}
