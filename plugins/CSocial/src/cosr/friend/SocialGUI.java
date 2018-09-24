package cosr.friend;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import cosr.roleplay.CRolePlay;
import cosr.roleplay.database.PlayerDataBase;

public class SocialGUI {
	
	/*����t�έ���*/
	public static FormWindowSimple homePage() {
		FormWindowSimple window = new FormWindowSimple("���歺��", "");
		window.addButton(new ElementButton(TextFormat.BOLD + "�n�ͦC��"));
		window.addButton(new ElementButton(TextFormat.BOLD + "��Q�t��"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�g�U�߱�"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��������")));
		return window;
	}
	
	/*�n�ͨt�ΩҦ���������*/
	public static FormWindowSimple friendListWindow(Player p) {
		FormWindowSimple window = new FormWindowSimple("�n�ͦC��", "�I��n�ͤw��o��h��T");
		window.addButton(new ElementButton(TextFormat.BOLD + "�s�W�n��"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�B�z�n�ͽШD"));
		for(String friend : SocialMain.FPOOL.get(p.getName())) {
			try {
				window.addButton(new ElementButton(friend + " Lv." + CRolePlay.getPlv(friend).getLv()));
			}catch(FileNotFoundException e) {
				window.addButton(new ElementButton(friend));
				continue;
			}
		}
		window.addButton(new ElementButton("��^�ܭ���"));
		
		return window;
	}
	
	public static FormWindowCustom newFriendWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("�s�W�n��");
		ElementDropdown onlinePlayerList = new ElementDropdown("�u�W���a");
		
		onlinePlayerList.addOption("None");
		for(Player p : SocialMain.getInstance().getServer().getOnlinePlayers().values()) {
			if(p.getName() != playerName)
				onlinePlayerList.addOption(p.getName());
		}
		window.addElement(new ElementInput("�п�J���a�W��"));
		window.addElement(onlinePlayerList);
		return window;
	}
	
	public static FormWindowSimple friendRequestWindow(String playerName) {
		ArrayList<String> rqList = SocialMain.getFriendRequests(playerName);
		FormWindowSimple window = new FormWindowSimple("�n�ͥӽЦC��", "");
		if(rqList != null) {
			for(String rq : rqList) {
				window.addButton(new ElementButton(rq));
			}
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	public static FormWindowSimple friendWindow(String friendName) {
		FormWindowSimple window = new FormWindowSimple(friendName, "");
		window.addButton(new ElementButton("�d�ݭӤH��T"));
		window.addButton(new ElementButton("��������"));
		window.addButton(new ElementButton("�H�H"));
		window.addButton(new ElementButton(TextFormat.RED + "�Ѱ��n�����Y"));
		window.addButton(new ElementButton("��^�ܦn�ͦC��"));
		
		return window;
	}
	
	public static FormWindowModal playerDataWindow(String playerName, String _who) throws FileNotFoundException {
		return new FormWindowModal("���a"+playerName+"���ӤH�ɮ�", playerInfo(_who), 
				"�[���n��", (SocialMain.getFriendRequests(playerName).contains(_who)? "�ڵ��n�ͥӽ�" : "����"));
	}
	
	public static FormWindowModal friendInfoWindow(String friendName) throws FileNotFoundException {
		return new FormWindowModal("�n��: " + friendName, playerInfo(friendName), "�T�w", "��^");
	}
	
	public static FormWindowModal sureToDelFriendWindow(String friendName) {
		return new FormWindowModal("�n��: " + friendName, "�T�{�R���Ӧn�Ͷ�?\n( ! �R����N�L�k�_��)", "�T�{", "����");
	}
	
	/*���B�t�ΩҦ���������*/
	public static FormWindowCustom proposingWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("�z�٨S����Q, ���֧�@��TA�a");
		
		ElementDropdown dropList = new ElementDropdown("�u�W���a");
		dropList.addOption("None");
		for(Player p : SocialMain.getInstance().getServer().getOnlinePlayers().values()) {
			if(p.getName() != playerName)
				dropList.addOption(p.getName());
		}
		window.addElement(dropList);
		
		return window;
	}
	
	public static FormWindowSimple marrySystemWindow() {
		FormWindowSimple window = new FormWindowSimple("���A����Q", "");
		window.addButton(new ElementButton("�d�ݦ�Q��T"));
		window.addButton(new ElementButton("��TA����"));
		window.addButton(new ElementButton("��������"));
		window.addButton(new ElementButton("�H�H"));
		window.addButton(new ElementButton(TextFormat.RED + "�Ѱ���Q���Y"));
		window.addButton(new ElementButton("��^�ܭ���"));
		
		return window;
	}
	
	public static FormWindowModal mateInfoWindow(String mateName) throws FileNotFoundException {
		Player mate = SocialMain.getInstance().getServer().getPlayer(mateName);
		String moodMsg = SocialMain.getMoodMsg(mateName);
		String content = TextFormat.DARK_GREEN + "��Q�W��: " + TextFormat.RESET + ((mate != null)? mate.getDisplayName() : mateName) + "\n" + 
						 TextFormat.DARK_GREEN + "��Q�ٸ�: " + TextFormat.RESET + CRolePlay.getPinnedTitle(mateName).body() + 
						 TextFormat.DARK_GREEN + "��Q����: " + TextFormat.RESET + CRolePlay.getPlv(mateName).getLv() + "\n" + 
						 TextFormat.DARK_GREEN + "�b�u���A: " + TextFormat.RESET + ((mate != null)? TextFormat.GREEN+"�b�u" : TextFormat.GRAY+"���u") + "\n" + 
						 TextFormat.DARK_GREEN + "�W���n�J: " + TextFormat.RESET + 
						 	new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new PlayerDataBase(mateName).loginMoment.getTime()) + "\n" + 
						 TextFormat.DARK_GREEN + "�߱��p�y: " + TextFormat.RESET + 
						 	(moodMsg != null? moodMsg : TextFormat.GRAY+"�o���]�k�v���i, ���򳣨S�d�U") + "\n";
		return new FormWindowModal("��Q: " + mateName, content, "�T�w", "��^");
	}
	
	public static FormWindowModal sureToDelMateWindow(String mateName) {
		return new FormWindowModal("��Q: " + mateName, "�z�T�w�n���}TA�F��?QQ\n", "�T�{", "����");
	}
	
	/*�߱��p�y*/
	public static FormWindowCustom moodMsgWindow(String user) {
		FormWindowCustom window = new FormWindowCustom("�g�U���誺�߱�");
		
		window.addElement(new ElementInput("�п�J�T��", "", (SocialMain.msgMap.containsKey(user)? SocialMain.msgMap.get(user) : "")));
		return window;
	}
	
	private static String playerInfo(String playerName) throws FileNotFoundException {
		Player friend = SocialMain.getInstance().getServer().getPlayer(playerName);
		String mateName = SocialMain.getMate(playerName);
		String moodMsg = SocialMain.getMoodMsg(playerName);
		String content = TextFormat.DARK_GREEN + "���a�W��: " + TextFormat.RESET + ((friend != null)? friend.getDisplayName() : playerName) + "\n" + 
						 TextFormat.DARK_GREEN + "���a����: " + TextFormat.RESET + CRolePlay.getPlv(playerName).getLv() + "\n" + 
						 TextFormat.DARK_GREEN + "���a�ٸ�: " + TextFormat.RESET + CRolePlay.getPinnedTitle(playerName).body() + "\n" + 
						 TextFormat.DARK_GREEN + "�b�u���A: " + TextFormat.RESET + ((friend != null)? TextFormat.GREEN+"�b�u" : TextFormat.GRAY+"���u") + "\n" + 
						 TextFormat.DARK_GREEN + "��Q: "     + TextFormat.RESET + ((mateName != null)? mateName : "�L") + "\n" + 
						 TextFormat.DARK_GREEN + "�W���n�J: " + TextFormat.RESET + 
						 	new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new PlayerDataBase(playerName).loginMoment.getTime()) + "\n" + 
						 TextFormat.DARK_GREEN + "�߱��p�y: " + TextFormat.RESET + 
						 	(moodMsg != null? moodMsg : TextFormat.GRAY+"�o���]�k�v���i, ���򳣨S�d�U") + "\n";
		//���a���|
		return content;
	}
}
