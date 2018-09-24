package cosr.newbie.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import cosr.newbie.BuildTool;
import cosr.newbie.CNewbie;

public class NewbieCommand extends Command {

	public NewbieCommand() {
		super("newbie", "command for newbie", "/newbie", new String[] {"nb"});
		this.commandParameters.clear();
		this.commandParameters.put("v", new CommandParameter[] {
				new CommandParameter("Level", false)
		});
		this.commandParameters.put("script", new CommandParameter[0]);
		this.commandParameters.put("end", new CommandParameter[0]);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(args.length < 1) return false;
		
		switch(args[0].toLowerCase()) {
		case "v":
			if(args.length < 2) {
				if(sender.isPlayer()) {
					CNewbie.NBVillage = ((Player)sender).getLevel();
				}else return false;
			}else {
				Level level = Server.getInstance().getLevelByName(args[1]);
				if(level == null) {
					sender.sendMessage(CNewbie.TITLE + TextFormat.RED + "���s�b�Ӧa��");
					return true;
				}
				CNewbie.NBVillage = level;
			}
			sender.sendMessage(CNewbie.TITLE + TextFormat.GREEN + "���\�N" + TextFormat.RESET + CNewbie.NBVillage.getFolderName() + TextFormat.GREEN + "�]���s���");
			break;
		case "script":
			if(sender.isPlayer()) {
				Player p = (Player) sender;
				if(CNewbie.getBuildingMap().containsKey(p.getName())) {
					p.sendMessage(CNewbie.TITLE + TextFormat.GRAY + "�z�w�B��}���]�m���A");
				}else {
					BuildTool bt = new BuildTool(p);
					CNewbie.getBuildingMap().put(p.getName(), bt);
					bt.prompt();
					bt.showEffectSelector();
				}
			}
			break;
		case "next":
			if(sender.isPlayer()) {
				Player p = (Player) sender;
				BuildTool bt = CNewbie.getBuildingMap().get(p.getName());
				if(bt != null) {
					if(bt.step == 1) {
						bt.step++;
						bt.prompt();
						bt.showDetectTypeSetter();
					}
				}else return false;
			}
			break;
		case "end":
			if(sender.isPlayer()) {
				Player p = (Player) sender;
				CNewbie.getEndSettingPool().add(p.getName());
				p.sendMessage(CNewbie.TITLE + TextFormat.YELLOW + "���I��ϰ�H�]�m���I");
				p.sendMessage(CNewbie.TITLE + TextFormat.WHITE + "�Y�������]�w�Щ��ѫǿ�J@cancel");
			}
			break;
		default:
			return false;
		}
		
		return true;
	}

}
