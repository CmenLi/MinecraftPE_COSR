package cosr.roleplay.gcollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import cosr.roleplay.CRolePlay;
import cosr.roleplay.PlayerTitle;
import cosr.roleplay.database.PlayerDataBase;

public class Title extends GameCollection {
	
	public enum Rarity {
		NORMAL("���q", TextFormat.GRAY),
		ADVANCE("����", TextFormat.AQUA),
		RARE("�}��", TextFormat.LIGHT_PURPLE),
		LEGEND("�ǩ_", TextFormat.GOLD),
		TABOO("�T��", TextFormat.RED), 
		COSR("�M��", TextFormat.YELLOW);
		
		private String name;
		private TextFormat color;
		private Rarity(String name, TextFormat color) {
			this.name = name;
			this.color = color;
		}
		public String getName() {
			return name;
		}
		public TextFormat getColor() {
			return color;
		}
		
	}
	
	public static final String TITLE_FILE_NAME = "Titles.yml";
	
	private Rarity rarity;
	
	public Title() {
		this("UNKNOWN", "����", "Unknown", "Unknown", "Unknown", Rarity.NORMAL);
	}
	
	public Title(String head) {
		this(head, new File(CRolePlay.getInstance().getDataFolder(), TITLE_FILE_NAME));
	}
	
	public Title(String head, File configFile) {
		this.head = head;
		this.loadFromConfig(new Config(configFile));
	}
	
	public Title(String head, String name, String description, String requirement, String reward, Rarity rarity) {
		this.head = head;
		this.name = name;
		this.description = description;
		this.requirement = requirement;
		this.reward = reward;
		this.rarity = rarity;
	}
	
	public Rarity getRarity() {
		return rarity;
	}

	public String msgTitleForm() {
		return TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.WHITE + "[" + this.getRarity().getColor() + this.getName() + TextFormat.WHITE + "]"))
				+ TextFormat.RESET;
	}
	
	public String body() {
		return this.msgTitleForm() + TextFormat.RESET + (TextFormat.GRAY + this.getDescription());
	}
	
	public String getterMessage() {
		String achv_msg;
		achv_msg = TextFormat.GREEN + "���ߧA��o�s�ٸ�: \n" 
					+ this.msgTitleForm() + TextFormat.RESET
					+ (TextFormat.GRAY + this.getDescription());
		return achv_msg;
	}
	
	public static Title get(String head) {
		String _head = head.toUpperCase();
		if(CRolePlay.getTitleMap().containsKey(_head)) {
			return CRolePlay.getTitleMap().get(_head);
		}else {
			return new Title(_head);
		}
	}

	public boolean grantTo(String targetName) throws FileNotFoundException {
		if(CRolePlay.getOnlinePDB().containsKey(targetName)) {
			Map<String, PlayerTitle> titleMap = CRolePlay.getOnlinePDB().get(targetName).getPlayerTitleMap();
			titleMap.put(this.head, new PlayerTitle(this, true));
			Player target = CRolePlay.getInstance().getServer().getPlayer(targetName);
			if(target != null) {
				target.sendMessage(getterMessage());
				return true;
			}
		}else {
			File file = new File(CRolePlay.getInstance().getDataFolder(), PlayerDataBase.PDBPATH + targetName + ".yml");
			if(file.exists()) {
				Config targetConf = new Config(file, Config.YAML);
				ConfigSection achvSection = targetConf.getSection("Titles");
				achvSection.set(this.head, new PlayerTitle(this).dataSection());
				targetConf.save();
				return true;
			}else {
				throw new FileNotFoundException();
			}
		}
		
		return false;
	}
	
	public String information() {
		String titleInfo = "";
		String separator = TextFormat.RESET + "=========================\n";
		titleInfo += TextFormat.RESET + (TextFormat.BOLD + (this.rarity.getColor() + this.head)) + "\n" + 
				TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��W��: ") + TextFormat.RESET + this.getName() + "\n" + 
				TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��~��: ") + TextFormat.RESET + 
				(this.getRarity().getColor() + this.getRarity().getName()) + "\n" + 
				TextFormat.RESET + (TextFormat.DARK_GREEN + "�ٸ��H��: ") + TextFormat.RESET + this.getDescription() + "\n" + 
				TextFormat.RESET + (TextFormat.DARK_GREEN + "�F������: ") + TextFormat.RESET + this.getRequirement() + "\n" + 
				TextFormat.RESET + (TextFormat.DARK_GREEN + "�F�����y: ") + TextFormat.RESET + this.getReward() + "\n" + 
				separator;
		
		return titleInfo;
	}
	
	@Override
	public ConfigSection dataSection() {
		return new ConfigSection() {
			private static final long serialVersionUID = 1L;
			{
				set("Name", name);
				set("Rarity", rarity.name());
				set("Quote", description);
				set("Require", requirement);
				set("Reward", reward);
			}
		};
	}

	@Override
	public void loadFromConfig(Config config) {
		// TODO Auto-generated method stub
		super.loadFromConfig(config);
		this.rarity = Rarity.valueOf(config.getString(head + ".Rarity").toUpperCase());
	}
}
