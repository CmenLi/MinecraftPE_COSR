package cosr.roleplay;

import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import cosr.roleplay.gcollection.Achievement;
import cosr.roleplay.gcollection.Title;

public class PluginInfo {
	
	public static String personalAchvList(Player p, int page) {
		LinkedHashMap<String, Boolean> playerAchvMap = CRolePlay.getOnlinePDB().get(p.getName()).getPlayerAchvMap();
		int maxIndex = playerAchvMap.size();
		int maxPage = maxIndex/4 + ((maxIndex%4 == 0)? 0:1);
		int realPage = (page > maxPage)? maxPage : page;
		if(realPage <= 0) realPage = 1;
		
		int startIndex = (realPage-1)*4;
		int endIndex = realPage*4;
		
		String title = TextFormat.RESET + (TextFormat.GREEN + "�ڪ����N�C��(page " + realPage + "/" + maxPage + ")\n");
		String separator = TextFormat.RESET + "=========================\n";
		
		String achvInfo = "";
		
		int i = 0;
		for(String head : playerAchvMap.keySet()) {
			//�q1�}�l
			i++;
			if(i > startIndex && i <= endIndex) {
				Achievement achv = new Achievement(head);
				if(achv != null) {
					achvInfo += TextFormat.RESET + (TextFormat.BOLD + (TextFormat.GREEN + achv.getHead())) + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "���N�W��: ") + TextFormat.RESET + achv.getName() + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "���N�H��: ") + TextFormat.RESET + achv.getDescription() + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�F������: ") + TextFormat.RESET + achv.getRequirement() + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�F�����y: ") + TextFormat.RESET + achv.getReward() + "\n" + 
							separator;
				}
			}
		}
		
		return title + separator + achvInfo + "\n";
	}
	
	public static String personalTitleList(Player p, int page) {
		LinkedHashMap<String, PlayerTitle> playerTitleMap = CRolePlay.getOnlinePDB().get(p.getName()).getPlayerTitleMap();
		int maxIndex = playerTitleMap.size();
		int maxPage = maxIndex/4 + ((maxIndex%4 == 0)? 0:1);
		int realPage = (page > maxPage)? maxPage : page;
		if(realPage <= 0) realPage = 1;
		
		int startIndex = (realPage-1)*4;
		int endIndex = realPage*4;
		
		String title = TextFormat.RESET + (TextFormat.GREEN + "�ڪ��ٸ��C��(page " + realPage + "/" + maxPage + ")\n");
		String separator = TextFormat.RESET + "=========================\n";
		
		String titleInfo = "";
		
		int i = 0;
		for(String head : playerTitleMap.keySet()) {
			//�q1�}�l
			i++;
			if(i > startIndex && i <= endIndex) {
				PlayerTitle pt = playerTitleMap.get(head);
				Title _title = pt.getTitle();
				titleInfo += TextFormat.RESET + (TextFormat.BOLD + (_title.getRarity().getColor() + _title.getHead())) + 
						TextFormat.GRAY + ((pt.isTag())? "(��e�Y��)" : "") + "\n" + 
						TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��W��: ") + TextFormat.RESET + _title.getName() + "\n" + 
						TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��~��: ") + TextFormat.RESET + 
						(_title.getRarity().getColor() + _title.getRarity().getName()) + "\n" + 
						TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��H��: ") + TextFormat.RESET + _title.getDescription() + "\n" + 
						TextFormat.RESET + (TextFormat.DARK_GREEN + "�F������: ") + TextFormat.RESET + _title.getRequirement() + "\n" + 
						TextFormat.RESET + (TextFormat.DARK_GREEN + "�F�����y: ") + TextFormat.RESET + _title.getReward() + "\n" + 
						separator;
			}
		}
		
		return title + separator + titleInfo + "\n";
	}

	public static String achievementList(Player p, int page) {
		
		int maxIndex = CRolePlay.getAchvMap().size();
		int maxPage = maxIndex/4 + ((maxIndex%4 == 0)? 0:1);
		int realPage = (page > maxPage)? maxPage : page;
		if(realPage <= 0) realPage = 1;
		
		int startIndex = (realPage-1)*4;
		int endIndex = realPage*4;
		
		String title = TextFormat.RESET + (TextFormat.GREEN + "���A�����N�C��(page " + realPage + "/" + maxPage + ")\n");
		String separator = TextFormat.RESET + "=========================\n";
		
		String achvInfo = "";
		
		int i = 0;
		for(String head : CRolePlay.getAchvMap().keySet()) {
			//�q1�}�l
			i++;
			if(i > startIndex && i <= endIndex) {
				Achievement achv = CRolePlay.getAchvMap().get(head);
				achvInfo += TextFormat.RESET + (TextFormat.BOLD + (TextFormat.GREEN + achv.getHead())) + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "���N�W��: ") + 
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerAchvMap().containsKey(achv.getHead()))?
								TextFormat.RESET + achv.getName() : "??????") + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "���N�H��: ") + 
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerAchvMap().containsKey(achv.getHead()))?
									TextFormat.RESET + achv.getDescription() : "??????") + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�F������: ") + 
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerAchvMap().containsKey(achv.getHead()))?
									TextFormat.RESET + achv.getRequirement() : "??????") + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�F�����y: ") +
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerAchvMap().containsKey(achv.getHead()))?
									TextFormat.RESET + achv.getReward() : "??????") + "\n" + 
							separator;
			}
		}
		
		return title + separator + achvInfo + "\n";
	}
	
	public static String titleList(Player p, int page) {
		
		int maxIndex = CRolePlay.getAchvMap().size();
		int maxPage = maxIndex/4 + ((maxIndex%4 == 0)? 0:1);
		int realPage = (page > maxPage)? maxPage : page;
		if(realPage <= 0) realPage = 1;
		
		int startIndex = (realPage-1)*4;
		int endIndex = realPage*4;
		
		String title = TextFormat.RESET + (TextFormat.GREEN + "���A���ٸ��C��(page " + realPage + "/" + maxPage + ")\n");
		String separator = TextFormat.RESET + "=========================\n";
		
		String titleInfo = "";
		
		int i = 0;
		for(String head : CRolePlay.getTitleMap().keySet()) {
			//�q1�}�l
			i++;
			if(i > startIndex && i <= endIndex) {
				Title _title = CRolePlay.getTitleMap().get(head);
				titleInfo += TextFormat.RESET + (TextFormat.BOLD + (_title.getRarity().getColor() + _title.getHead())) + "\n" + 
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��W��: ") + 
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerTitleMap().containsKey(_title.getHead()))?
								TextFormat.RESET + _title.getName() : "??????") + "\n" + 
							
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��~��: ") + 
								TextFormat.RESET + _title.getRarity().getColor() + _title.getRarity().getName() + "\n" + 
							
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��H��: ") + 
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerTitleMap().containsKey(_title.getHead()))?
									TextFormat.RESET + _title.getDescription() : "??????") + "\n" + 
							
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�F������: ") + 
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerTitleMap().containsKey(_title.getHead()))?
									TextFormat.RESET + _title.getRequirement() : "??????") + "\n" + 
							
							TextFormat.RESET + (TextFormat.DARK_GREEN + "�F�����y: ") +
							((CRolePlay.getOnlinePDB().get(p.getName()).getPlayerTitleMap().containsKey(_title.getHead()))?
									TextFormat.RESET + _title.getReward() : "??????") + "\n" + 
							separator;
			}
		}
		
		return title + separator + titleInfo + "\n";
	}
	
	public static String levelStatus(Player p) {
		//TODO: ���a���ūH��
		PlayerLevel plv = CRolePlay.getOnlinePDB().get(p.getName()).plv;
		return TextFormat.RESET + 
				(TextFormat.DARK_AQUA + "�z��e�����Ŭ�: ") + TextFormat.RESET + plv.getLv() + "\n" + 
				(TextFormat.DARK_AQUA + "�z��e��o���g��: ") + TextFormat.RESET + plv.getExp() + "/" + plv.getLevelUpExp() + "\n" + 
				(TextFormat.DARK_AQUA + "�Z�U���ɯ��ٻ�: ") + TextFormat.RESET + (plv.getLevelUpExp() - plv.getExp()) + "\n";
	}
}
