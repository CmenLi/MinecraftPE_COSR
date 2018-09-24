package cosr.ess;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.utils.TextFormat;
import cosr.economy.CEconomy;
import cosr.economy.job.CJob;
import cosr.roleplay.CRolePlay;
import cosr.roleplay.gcollection.Title;

public class DataWindow {

	public static void showDataWindow(Player p) {
		FormWindowModal window = new FormWindowModal("�ڪ��򥻸�T", "", "�T�{", "����");
		Title pinned = CRolePlay.getPinnedTitle(p.getName());
		CJob cjob = CEconomy.getJobMap().get(p.getName());
		String levelName = p.getLevel().getFolderName();
		window.setContent(TextFormat.DARK_AQUA + "�����: \n" + 
				TextFormat.DARK_AQUA + "�ٸ�: " + (pinned != null? pinned.getRarity().getColor() + pinned.getName() : "None") + "\n" + 
				TextFormat.DARK_AQUA + "�u�@: " + TextFormat.WHITE + (cjob != null? cjob.getJob().chineseName() : "None") + "\n" + 
				TextFormat.DARK_AQUA + "�Ҧb�@��: " + TextFormat.WHITE + (p.isOp()? levelName : cosr.multiworld.Main.getWorldsConfig().get(levelName)) + "\n" + 
				"\n" + TextFormat.RESET + 
				TextFormat.DARK_GREEN + "������A��: \n" + 
				TextFormat.DARK_GREEN+ "�b�u�H��: " + TextFormat.WHITE + Essential.getInstance().getServer().getOnlinePlayers().size() + "\n");
		
		p.showFormWindow(window);
	}
	
}
