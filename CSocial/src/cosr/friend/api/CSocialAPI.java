package cosr.friend.api;

import java.io.File;
import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cosr.friend.SocialMain;

public class CSocialAPI {

	public static Item socialItem() {
		Item item = Item.get(450);
		item.setCustomName(TextFormat.ITALIC + (TextFormat.LIGHT_PURPLE + "�s�]���p�H��"));
		item.setLore(TextFormat.ITALIC + (TextFormat.YELLOW + "ť�F�s�]�ѷݪ��@�u�ܫ�, "), 
				TextFormat.ITALIC + (TextFormat.YELLOW + "�٬O�M�w��o�F��a�W�F..."), 
				TextFormat.ITALIC + (TextFormat.YELLOW + "�W���g��: " + TextFormat.GRAY + "\"�A�j���^���]�ݭn�B��\""));
		return item.clone();
	}
	
	public static void makeFriend(String playerName, String otherName) {
		Player p = Server.getInstance().getPlayer(playerName);
		if (SocialMain.FPOOL.get(playerName).contains(otherName)) {
			if(p != null)
				p.sendMessage(TextFormat.GRAY + "�Ӫ��a�w�g�O�z���n���o");
			return;
		}
		Player target = SocialMain.getInstance().getServer().getPlayer(otherName);
		if (target != null) {
			//��Ҥƹ�誺�n�ͽШD��
			if (!SocialMain.friendRequestPool.containsKey(otherName)) {
				SocialMain.friendRequestPool.put(otherName, new ArrayList<String>());
			}
			SocialMain.friendRequestPool.get(otherName).add(p.getName());
			p.sendMessage(TextFormat.DARK_GREEN + "�w�ǰe�n���ܽе�" + otherName);
			target.sendMessage(TextFormat.ITALIC
					+ (TextFormat.YELLOW + "���a" + p.getName() + "�V�z���X�F�n���ܽ�\n")
					+ TextFormat.RESET + "��J/cfriend accept " + playerName + "  ����\n"
					+ TextFormat.RESET + "��J/cfriend deny " + playerName + "  �ڵ�");
		} else {
			File file = new File(SocialMain.getInstance().getDataFolder(), otherName + ".yml");
			if (!file.exists()) {
				p.sendMessage(TextFormat.RED + "�䤣��Ӫ��a");
				return;
			}

			Config conf = new Config(file);
			if (!conf.exists("friend_requests")) {
				conf.set("friend_requests", new ArrayList<String>());
			}
			conf.getStringList("friend_requests").add(p.getName());
			conf.save();
			p.sendMessage(TextFormat.DARK_GREEN + "�w�ǰe�n���ܽе�" + otherName);
		}
	}
}
