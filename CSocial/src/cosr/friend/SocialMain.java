/*
 * mate: 
 * friends:
 * - 
 * - 
 * - 
 * friend_requests:
 * - 
 * - 
 * - 
 */

package cosr.friend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cosr.friend.api.CSocialAPI;
import cosr.mcpemail.Mail;
import cosr.roleplay.CRolePlay;
import cosr.roleplay.database.PlayerDataBase;

public class SocialMain extends PluginBase {
	
	public static final String infoTitle = TextFormat.RESET + 
			(TextFormat.BOLD + (TextFormat.WHITE + "[") + (TextFormat.DARK_AQUA + "CSocial") + (TextFormat.WHITE + "]"));
	
	public static Map<String, ArrayList<String>> FPOOL = new HashMap<String, ArrayList<String>>();					//�C�Ӫ��a���B�͸s
	public static Map<String, String> MPOOL = new HashMap<String, String>();										//�C�Ӫ��a���t��
	public static Map<String, ArrayList<String>> friendRequestPool = new HashMap<String, ArrayList<String>>();		//�C�Ӫ��a���쪺�n���ܽ�
	public static Map<String, String> proposingPool = new HashMap<String, String>();								//�C�Ӫ��a���D�B�ШD
	public static Set<String> breakingSet = new HashSet<String>();													//�C�Ӫ��a�����B�ШD
	public static Map<String, String> msgMap;																		//�C�Ӫ��a���߱��p�y
	
	private static SocialMain plugin;
	
	public static SocialMain getInstance() {
		return SocialMain.plugin;
	}
	
	public static ArrayList<String> getFriendRequests(String playerName) {
		return friendRequestPool.get(playerName);
	}
	
	public void onEnable() {
		plugin = this;
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		msgMap = new HashMap<String, String>();
		
		File dataFolder = this.getDataFolder();
		for(File playerFile : dataFolder.listFiles()) {
			Config conf = new Config(playerFile);
			String playerName = playerFile.getName().replace(".yml", "");
			FPOOL.put(playerName, new ArrayList<String>(conf.getStringList("friends")));
			friendRequestPool.put(playerName, new ArrayList<String>(conf.getStringList("friend_requests")));
		}
	}
	
	public void onDisable() {
		Config conf;
		for(String playerName : MPOOL.keySet()) {
			conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
			conf.set("mate", MPOOL.get(playerName));
			conf.save();
			conf = new Config(new File(this.getDataFolder(), MPOOL.get(playerName) + ".yml"));
			conf.set("mate", playerName);
			conf.save();
		}
		for(String playerName : FPOOL.keySet()) {
			conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
			conf.set("friends", FPOOL.get(playerName));
			conf.save();
		}
		for(String playerName : friendRequestPool.keySet()) {
			conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
			conf.set("friend_requests", friendRequestPool.get(playerName));
			conf.save();
		}
		for(String playerName : msgMap.keySet()) {
			conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
			conf.set("mood-msg", msgMap.get(playerName));
			conf.save();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("csocial")) {
			if(args.length < 1) return false;
			
			if(args[0].equals("save-all")) {
				if(!sender.isOp()) return false;
				Config conf;
				for(String playerName : MPOOL.keySet()) {
					conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
					conf.set("mate", MPOOL.get(playerName));
					conf.save();
					conf = new Config(new File(this.getDataFolder(), MPOOL.get(playerName) + ".yml"));
					conf.set("mate", playerName);
					conf.save();
				}
				for(String playerName : FPOOL.keySet()) {
					conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
					conf.set("friends", FPOOL.get(playerName));
					conf.save();
				}
				for(String playerName : friendRequestPool.keySet()) {
					conf = new Config(new File(this.getDataFolder(), playerName + ".yml"));
					conf.set("friend_requests", friendRequestPool.get(playerName));
					conf.save();
				}
			}
			else if(args[0].equals("motto")) {
				if(args[1].equals("set")) {
					if(args.length < 3) return false;
					
					if(sender.isPlayer()) {
						msgMap.put(sender.getName(), args[2]);
						sender.sendMessage(TextFormat.GREEN + "�߱��p�y�]�w���\!");
					}else
						sender.sendMessage(TextFormat.RED + "�Цb�C�������榹���O");
				}
			}
			else if(args[0].equals("ui")) {
				if(sender.isPlayer()) {
					Player p = (Player) sender;
					p.showFormWindow(SocialGUI.homePage());
				}else
					sender.sendMessage(TextFormat.RED + "�Цb�C�������榹���O");
			}
			else if(args[0].equals("check")) {
				if(!sender.isOp()) return false;
				String mlist = "";
				for(String playerName : MPOOL.keySet()) {
					mlist += playerName + "---" + MPOOL.get(playerName) + "\n";
				}
				sender.sendMessage("MPOOL: \n" + mlist);
			}
			else if(args[0].equalsIgnoreCase("doll")) {
				if(sender.isPlayer()) {
					Player p = (Player) sender;
					Item doll = CSocialAPI.socialItem();
					if (p.getInventory().canAddItem(doll)) {
						p.getInventory().addItem(doll);
						p.sendMessage(TextFormat.GREEN + "�z��o�F" + TextFormat.RESET + doll.getName() + TextFormat.RESET + "x1");
					}
				}
			}
 		}
		if(cmd.getName().equals("cfriend")) {
			if(!sender.isPlayer()) {
				sender.sendMessage(TextFormat.RED + "�Цb�C�������榹���O");
				return true;
			}
			if(args.length < 1) return false;
			
			Player p = (Player) sender;
			if(args[0].equals("help")) {
				
			}
			else if(args[0].equals("my")) {
				String friendList = "";
				for(String friendName : FPOOL.get(p.getName())) {
					friendList += friendName + "\n";
				}
				
				p.sendMessage("--- �z���n�ͦC�� ---\n" + friendList);
			}else if(args[0].equals("requests")) {
				String requestList = "";
				if(!friendRequestPool.containsKey(p.getName())) {
					friendRequestPool.put(p.getName(), new ArrayList<String>());
				}
				for(String request : friendRequestPool.get(p.getName())) {
					requestList += request + "\n";
				}
				p.sendMessage("�Ҧ����B�z���n���ܽ�: \n" + requestList);
			}
			else if(args[0].equals("add")) {
				if(args.length < 2) return false;
				
				if(FPOOL.get(p.getName()).contains(args[1])) {
					p.sendMessage(TextFormat.GRAY + "�Ӫ��a�w�g�O�z���n���o");
					return true;
				}
				
				if(args[1].equals(p.getName())) {
					p.sendMessage(TextFormat.RED + "�L�k�N�ۤv�[���n��");
					return true;
				}
				
				Player target = this.getServer().getPlayer(args[1]);
				if(target != null) {
					if(!friendRequestPool.containsKey(args[1])) {
						friendRequestPool.put(args[1], new ArrayList<String>());
					}
					friendRequestPool.get(args[1]).add(p.getName());
					target.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "���a" + p.getName() + "�V�z���X�F�n���ܽ�\n")
							+ TextFormat.RESET + "��J/cfriend accept " + p.getName() + "  ����\n"
							+ TextFormat.RESET + "��J/cfriend deny " + p.getName() + "  �ڵ�");
				}else {
					File file = new File(this.getDataFolder(), args[1] + ".yml");
					if(!file.exists()) {
						p.sendMessage(TextFormat.RED + "�䤣��Ӫ��a");
						return true;
					}
					
					Config conf = new Config(file);
					if(!conf.exists("friend_requests")) {
						conf.set("friend_requests", new ArrayList<String>());
					}
					conf.getStringList("friend_requests").add(p.getName());
					conf.save();
				}
			}
			else if(args[0].equals("retract")) {
				if(args.length < 2) return false;
				
				if(friendRequestPool.containsKey(args[1])) {
					if(friendRequestPool.get(args[1]).contains(p.getName())) {
						friendRequestPool.get(args[1]).remove(p.getName());
						p.sendMessage(TextFormat.GRAY + "�w���\�M�^�n���ܽ�");
						return true;
					}
				}
				//�Y�W�z�����ҵL�F���d���榹
				p.sendMessage(TextFormat.GRAY + "�z�|����Ӫ��a�e�X����n���ܽ�");
			}
			else if(args[0].equals("accept")) {
				if(args.length < 2) return false;
				
				if(friendRequestPool.containsKey(p.getName())) {
					if(friendRequestPool.get(p.getName()).contains(args[1])) {
						p.sendMessage(TextFormat.GREEN + "���\����" + args[1] + "���n���ܽ�");
						if(!FPOOL.containsKey(p.getName())) {
							FPOOL.put(p.getName(), new ArrayList<String>());
						}
						FPOOL.get(p.getName()).add(args[1]);
						
						Player requestOne = this.getServer().getPlayer(args[1]);
						if(requestOne != null) {
							requestOne.sendMessage(TextFormat.GREEN + "���a" + p.getName() + "�����F�z���n���ܽ�!");
							FPOOL.get(args[1]).add(p.getName());
						}else {
							File file = new File(this.getDataFolder(), args[1] + ".yml");
							Config conf = new Config(file);
							conf.getStringList("friends").add(p.getName());
							conf.save();
						}
						
						friendRequestPool.get(p.getName()).remove(args[1]);
						if(friendRequestPool.containsKey(args[1])) {
							if(friendRequestPool.get(args[1]).contains(p.getName()))
								friendRequestPool.get(args[1]).remove(p.getName());
						}
						return true;
					}
				}
				p.sendMessage(TextFormat.GRAY + "�L�Ӫ��a���n���ܽ�");
			}
			else if(args[0].equals("deny")) {
				if(args.length < 2) return false;
				
				if(friendRequestPool.containsKey(p.getName())) {
					if(friendRequestPool.get(p.getName()).contains(args[1])) {
						p.sendMessage(TextFormat.GRAY + "�z�ڵ��F" + args[1] + "���n���ܽ�");
						friendRequestPool.get(p.getName()).remove(args[1]);
						return true;
					}
				}
				p.sendMessage(TextFormat.GRAY + "�L�Ӫ��a���n���ܽ�");
			}
			else if(args[0].equals("remove")) {
				if(args.length < 2) return false;
				
				ArrayList<String> friendList = FPOOL.get(p.getName());
				if(friendList.contains(args[1])) {
					friendList.remove(args[1]);
					
					if(FPOOL.containsKey(args[1])) {
						FPOOL.get(args[1]).remove(p.getName());
					}else {
						File file = new File(this.getDataFolder(), args[1] + ".yml");
						if(!file.exists()) {
							p.sendMessage(TextFormat.RED + "�䤣��Ӫ��a");
							return true;
						}
						
						Config conf = new Config(file);
						if(!conf.exists("friends")) {
							conf.set("friends", new ArrayList<String>());
						}
						if(!conf.getStringList("friends").contains(p.getName())) {
							p.sendMessage(TextFormat.RED + "�z���b�Ӫ��a���n�ͦW�椺");
							return true;
						}
						conf.getStringList("friends").remove(p.getName());
						conf.save();
					}
					p.sendMessage(TextFormat.GRAY + "�w�N���a" + args[1] + "�q�z���n�ͦW�椤����");
					
				}else {
					p.sendMessage(TextFormat.RED + "�Ӫ��a���b�z���n�ͦW�椺");
				}
			}
			else
				return false;
		}
		else if(cmd.getName().equals("cmarry")) {
			if(!sender.isPlayer()) {
				sender.sendMessage(TextFormat.RED + "�Цb�C�������榹���O");
				return true;
			}
			if(args.length < 1) return false;
			
			Player p = (Player) sender;
			if(args[0].equals("help")) {
				
			}
			else if(args[0].equals("my")) {
				String mate = getMate(p.getName());
				if((mate != null)) {
					p.sendMessage(TextFormat.AQUA + "�z����Q�O: " + TextFormat.RESET + mate + "\n" + 
							((this.getServer().getPlayer(mate) != null)? 
							TextFormat.GREEN + "�{�b���b�u�W, ���֥h��TA�a!" : TextFormat.GRAY + "�{�b���A�u�W, ���֧�TA�Ӫ��a!"));
					
				}else {
					p.sendMessage(TextFormat.GRAY + "�z��e�٨S����Q, ���֥h��@�ӧa(#");
				}
			}
			else if(args[0].equals("propose")) {
				if(args.length < 2) return false;
				
					Player target = this.getServer().getPlayer(args[1]);
					
					if(args[1].equals(p.getName())) {
						p.sendMessage(TextFormat.RED + "�L�k�V�ۤv�i��www");
						return true;
					}
					
					if(proposingPool.containsValue(p.getName())) {
						p.sendMessage(TextFormat.GRAY + "��p! �@�H�L�k�P�ɦV�h�ӤH�D�B��");
						p.sendMessage(TextFormat.YELLOW + "�O�A�T�ߤG�N�F, ����VTA��ܨǤ���a!");
						return true;
					}
					
					if(MPOOL.containsValue(args[1]) || MPOOL.containsKey(args[1])) {
						p.sendMessage(TextFormat.GRAY + "�ӹ�H�w�g���t���o, �L�k�VTA�D�B�S......");
						return true;
					}
					if(proposingPool.containsKey(args[1])) {
						p.sendMessage(TextFormat.GRAY + "�ӹ�H�w�g����L�H���b�D�B�o, �ٽеy��O!");
						p.sendMessage(TextFormat.YELLOW + "(�p����: �H�ʫH���L, ���L���D�A���߷N�a)");
						return true;
					}
					
					if(target != null) {
						proposingPool.put(args[1], p.getName());
						target.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "���a" + p.getName() + "�V�z�D�B�F!! >///<\n")
								+ TextFormat.RESET + "��J/cmarry accept ����TA���߷N><\n"
								+ TextFormat.RESET + "��J/cmarry deny  ����TA���߷NQQ");
					}else {
						p.sendMessage(TextFormat.GRAY + "�ӹ�H���G���b�u�W, ��TA�W�u�F�A�V�L��F�߷N�a!");
					}
			}
			else if(args[0].equals("retract")) {
					if(proposingPool.containsValue(p.getName())) {
						proposingPool.remove(getKeyByValue(proposingPool, p.getName()));
						p.sendMessage(TextFormat.GRAY + "�w���\�M�^���B�ШD");
					}else
						p.sendMessage(TextFormat.GRAY + "�z��e�S���e�X���󵲱B�ШD!");
				
			}
			else if(args[0].equals("accept")) {
				try {
					if(proposingPool.containsKey(p.getName())) {
						MPOOL.put(proposingPool.get(p.getName()), p.getName());
						p.sendTitle(TextFormat.RED + "����!", "�z�����F" + proposingPool.get(p.getName()) + "���a���D�B, ���������ҩd >///<");
						String ta_name = proposingPool.get(p.getName());
						Player ta = this.getServer().getPlayer(ta_name);
						
						if(ta != null) ta.sendTitle(TextFormat.RED + "����!", "���a" + p.getName() + "�����F�z���D�B, ���������ҩd >///<");
						
						new Mail(TextFormat.RED + "GM", ta_name, TextFormat.RED + "�t�ιD�P�H��", 
								"����! ���a"+p.getName()+"�����F�z���D�B!\n" + 
								"�ڭ̬۫H�o�O�@�ؽt��, �Ʊ�A�̯���n�n�g��o���}�t, \n" + 
								"�ڭ̤]�N�|���h����, �H�����o���P��\n\n" + 
								TextFormat.RESET + "COSR�ζ� �q�W").sendOut();
						
						new Mail(TextFormat.RED + "GM", p.getName(), TextFormat.RED + "�t�ιD�P�H��", 
								"����! �z�P���a" +ta_name+"���������ҩd!\n" + 
								"�ڭ̬۫H�o�O�@�ؽt��, �Ʊ�A�̯���n�n�g��o���}�t, \n" + 
								"�ڭ̤]�N�|���h����, �H�����o���P��\n\n" + 
								TextFormat.RESET + "COSR�ζ� �q�W").sendOut();
						
						CRolePlay.getAchvMap().get("HEARTANDSOUL").grantTo(p.getName());
						CRolePlay.getAchvMap().get("HEARTANDSOUL").grantTo(ta_name);
						Server.getInstance().broadcastMessage(TextFormat.ITALIC + (TextFormat.BOLD + (TextFormat.YELLOW + "����!! " + 
																TextFormat.AQUA + "���a" + TextFormat.WHITE + ta_name + 
																TextFormat.AQUA + "�P���a" + TextFormat.WHITE + p.getName() + 
																TextFormat.RED + "�����ҩd" + TextFormat.AQUA + "�F!!\n" + 
																TextFormat.GOLD + "�Фj�a���L���m�W�̦n�����֧a!")));
						
						proposingPool.remove(p.getName());
						
					}else {
						p.sendMessage(TextFormat.GRAY + "�z��e�S��������󵲱B�ШD, ���p�A���@�q�ɶ��ݬݧa!");
					}
				}catch(FileNotFoundException err) {
					//catch
				}
					
			}
			else if(args[0].equals("deny")) {
					if(proposingPool.containsKey(p.getName())) {
						p.sendMessage(TextFormat.GRAY + "�z�ڵ��F���a"+proposingPool.get(p.getName())+"���ШD!");
						Player ta = this.getServer().getPlayer(proposingPool.get(p.getName()));
						
						if(ta != null) {
							ta.sendMessage(TextFormat.RED + "���a"+p.getName()+"�ڵ��F�z���D�B......\n" + 
											TextFormat.GRAY + "�γ\�O�߷N�S���ǹF��, ���L�S���Y, �ǳƦn��U�@���|��n!");
						}else {
							new Mail(TextFormat.RED + "GM", proposingPool.get(p.getName()), TextFormat.RED + "�t�ΫH��", 
									TextFormat.RED + "���a"+p.getName()+"�ڵ��F�z���D�B......\n" + 
									TextFormat.GRAY + "�γ\�O�߷N�S���ǹF��, ���L�S���Y, �ǳƦn��U�@���|��n!\n\n" + 
									TextFormat.RESET + "COSR�ζ� �q�W").sendOut();
						}
						
						proposingPool.remove(p.getName());
						
					}else {
						p.sendMessage(TextFormat.GRAY + "�z��e�S��������󵲱B�ШD, ���p�A���@�q�ɶ��ݬݧa!");
					}
			}
			else if(args[0].equals("tp")) {
				String mateName = getMate(p.getName());
				if(mateName != null) {
					Player mate = this.getServer().getPlayer(mateName);
					if(mate != null) {
						p.teleport(mate);
						mate.sendMessage(TextFormat.GOLD + "�z����Q�ӧ�z�F!");
					}else
						p.sendMessage(TextFormat.GRAY + "�z����Q�ثe���b�u�W, ���֧�L�Ӫ��a!");
				}else
					p.sendMessage(TextFormat.GRAY + "�z��e�٨S����Q, ���֥h��@�ӧa(#");
			}
			else if(args[0].equals("break")) {
				String mateName = getMate(p.getName());
				if(mateName != null) {
					breakingSet.add(p.getName());
					p.sendMessage(TextFormat.RED + "�z�u���n���}TA�F��...?");
					Player mate = this.getServer().getPlayer(mateName);
					
					if(mate != null) {
						p.sendMessage(TextFormat.GRAY + "�w�e�X���B�ШD, ���ݹ��^��......");
						mate.sendMessage(TextFormat.RED + "�z����Q"+p.getName()+"�V�z���X�F���B�ШD......\n" + 
											"�A��ѫǿ�J@Y�H��ܦP�N\n" + 
											"�A��ѫǿ�J@N��ܩڵ�");
					}else {
						if(isLongTimeNoLogin(mateName)) {
							p.sendMessage(TextFormat.GRAY + "��襼�W�u�ɹj�w�[, �L�������B���\");
							
							new Mail(TextFormat.RED + "GM", mateName, TextFormat.RED + "�t�ΫH��", 
									TextFormat.RED + "���a"+p.getName()+"���X�F���B�n�D\n" + 
									TextFormat.GRAY + "�ѩ�z�Ӥ[�S���W�u, ���F���@���a�v�Q, �w�L����۰����B\n\n" + 
									  				  "�ڭ̹惡�P���p, �Y�z��������D�β�ĳ, �w���pô�ڭ�\n\n" + 
									TextFormat.RESET + "COSR�ζ� �q�W").sendOut();
							
							breakingSet.remove(p.getName());
							MPOOL.remove(p.getName());
						}else {
							p.sendMessage(TextFormat.GRAY + "�z����Q���b�u�W, ���B����H�P�N!");
						}
					}
				}else
					p.sendMessage(TextFormat.GRAY + "�z��e�٨S����Q, ���֥h��@�ӧa(#");
			}
			else
				return false;
		}
		return true;
	}
	
	public static Object getKeyByValue(Map<? extends Object, ? extends Object> map, Object value) {
		for(Object o : map.keySet()) {
			if(map.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
	
	public static String getMate(String playerName) {
		if(MPOOL.containsKey(playerName)) {
			return MPOOL.get(playerName);
		}else {
			String mateName = (String) getKeyByValue(MPOOL, playerName);
			if(mateName != null) {
				return mateName;
			}else {
				Config conf = new Config(new File(SocialMain.getInstance().getDataFolder(), playerName+".yml"), Config.YAML);
				if(conf.exists("mate")) {
					return conf.getString("mate");
				}else
					return null;
			}
		}
	}
	
	public static String getMoodMsg(String playerName) {
		if(msgMap.containsKey(playerName)) {
			return msgMap.get(playerName);
		}else {
			Config conf = new Config(new File(SocialMain.getInstance().getDataFolder(), playerName + ".yml"), Config.YAML);
			if(conf.exists("mood-msg")) {
				return conf.getString("mood-msg");
			}else {
				return null;
			}
		}
	}
	
	//TODO: ����CRolePlay��PlayerDataBase
	public static boolean isLongTimeNoLogin(String playerName) {
		PlayerDataBase pdb = new PlayerDataBase(playerName);
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		
		if(now.get(Calendar.YEAR) > pdb.loginMoment.get(Calendar.YEAR) || 
				now.get(Calendar.MONTH) > pdb.loginMoment.get(Calendar.MONTH) ||
				now.get(Calendar.DAY_OF_MONTH) - pdb.loginMoment.get(Calendar.DAY_OF_MONTH) >= 14) {
			return true;
		}else
			return false;
	}
}
