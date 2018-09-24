package cosr.cnpc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cmen.essalg.CJEF;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.StringTag;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cosr.cnpc.entities.*;
import cosr.cnpc.type.*;

public class CNPCMain extends PluginBase implements Listener {
	
	private static Map<String, Entity> npcEditRequestions = new HashMap<String, Entity>();
	private static LinkedList<String> killerPool = new LinkedList<String>();
	private static Map<Long, Entity> NPCPool = new HashMap<Long, Entity>();
	
	private static final List<String> ENTITY_LIST = (List<String>) Arrays.asList("Bat", "Blaze", "CaveSpider", "Chicken", "Cow", "Creeper",
            "Donkey", "ElderGuardian", "EnderDragon", "Enderman", "Endermite", "Evoker", "Ghast", "Guardian",
            "Horse", "Human", "Husk", "IronGolem", "Lama", "MCow", "Magmacube", "Mule", "Ocelot", "Pig",
            "PolarBear", "Rabbit", "SHorse", "Sheep", "Shulker", "Silverfish", "Skeleton", "Slime",
            "Snowman", "Spider", "Squid", "Stray", "Vex", "Villager", "Vindicator", "Witch", "Wither",
            "Witherskeleton" , "Wolf", "ZHorse", "Zombie", "ZombiePigman", "ZombieVillager", "Boat", "Minecart");
	
	public static Map<String, Entity> getNpcEditRequestions() {
		return npcEditRequestions;
	}
	
	public static LinkedList<String> getKillerPool() {
		return killerPool;
	}

	public void registerNPC(){
        Entity.registerEntity(NPC_Bat.class.getSimpleName(), NPC_Bat.class);
        Entity.registerEntity(NPC_Chicken.class.getSimpleName(), NPC_Chicken.class);
        Entity.registerEntity(NPC_Cow.class.getSimpleName(), NPC_Cow.class);
        Entity.registerEntity(NPC_Donkey.class.getSimpleName(), NPC_Donkey.class);
        Entity.registerEntity(NPC_Horse.class.getSimpleName(), NPC_Horse.class);
        Entity.registerEntity(NPC_MCow.class.getSimpleName(), NPC_MCow.class);
        Entity.registerEntity(NPC_Mule.class.getSimpleName(), NPC_Mule.class);
        Entity.registerEntity(NPC_Ocelot.class.getSimpleName(), NPC_Ocelot.class);
        Entity.registerEntity(NPC_Pig.class.getSimpleName(), NPC_Pig.class);
        Entity.registerEntity(NPC_PolarBear.class.getSimpleName(), NPC_PolarBear.class);
        Entity.registerEntity(NPC_Rabbit.class.getSimpleName(), NPC_Rabbit.class);
        Entity.registerEntity(NPC_Sheep.class.getSimpleName(), NPC_Sheep.class);
        Entity.registerEntity(NPC_SHorse.class.getSimpleName(), NPC_SHorse.class);
        Entity.registerEntity(NPC_Villager.class.getSimpleName(), NPC_Villager.class);
        Entity.registerEntity(NPC_Wolf.class.getSimpleName(), NPC_Wolf.class);
        Entity.registerEntity(NPC_ZHorse.class.getSimpleName(), NPC_ZHorse.class);
        Entity.registerEntity(NPC_ElderGuardian.class.getSimpleName(), NPC_ElderGuardian.class);
        Entity.registerEntity(NPC_Guardian.class.getSimpleName(), NPC_Guardian.class);
        Entity.registerEntity(NPC_Snowman.class.getSimpleName(), NPC_Snowman.class);
        Entity.registerEntity(NPC_Lama.class.getSimpleName(), NPC_Lama.class);
        Entity.registerEntity(NPC_Squid.class.getSimpleName(), NPC_Squid.class);
        Entity.registerEntity(NPC_Villager.class.getSimpleName(), NPC_Villager.class);
        
        Entity.registerEntity(NPC_Vindicator.class.getSimpleName(), NPC_Vindicator.class);
        Entity.registerEntity(NPC_Vex.class.getSimpleName(), NPC_Vex.class);
        Entity.registerEntity(NPC_IronGolem.class.getSimpleName(), NPC_IronGolem.class);
        Entity.registerEntity(NPC_Blaze.class.getSimpleName(), NPC_Blaze.class);
        Entity.registerEntity(NPC_EnderDragon.class.getSimpleName(), NPC_EnderDragon.class);
        Entity.registerEntity(NPC_Wither.class.getSimpleName(), NPC_Wither.class);
        Entity.registerEntity(NPC_Ghast.class.getSimpleName(), NPC_Ghast.class);
        Entity.registerEntity(NPC_CaveSpider.class.getSimpleName(), NPC_CaveSpider.class);
        Entity.registerEntity(NPC_Creeper.class.getSimpleName(), NPC_Creeper.class);
        Entity.registerEntity(NPC_Enderman.class.getSimpleName(), NPC_Enderman.class);
        Entity.registerEntity(NPC_Endermite.class.getSimpleName(), NPC_Endermite.class);
        Entity.registerEntity(NPC_ZombiePigman.class.getSimpleName(), NPC_ZombiePigman.class);
        Entity.registerEntity(NPC_Silverfish.class.getSimpleName(), NPC_Silverfish.class);
        Entity.registerEntity(NPC_Skeleton.class.getSimpleName(), NPC_Skeleton.class);
        Entity.registerEntity(NPC_Spider.class.getSimpleName(), NPC_Spider.class);
        Entity.registerEntity(NPC_Stray.class.getSimpleName(), NPC_Stray.class);
        Entity.registerEntity(NPC_Witch.class.getSimpleName(), NPC_Witch.class);
        Entity.registerEntity(NPC_Husk.class.getSimpleName(), NPC_Husk.class);
        Entity.registerEntity(NPC_Zombie.class.getSimpleName(), NPC_Zombie.class);
        Entity.registerEntity(NPC_ZombieVillager.class.getSimpleName(), NPC_ZombieVillager.class);
        Entity.registerEntity(NPC_Evoker.class.getSimpleName(), NPC_Evoker.class);
        Entity.registerEntity(NPC_Shulker.class.getSimpleName(), NPC_Shulker.class);
        Entity.registerEntity(NPC_Slime.class.getSimpleName(), NPC_Slime.class);
        Entity.registerEntity(NPC_Witherskeleton.class.getSimpleName(), NPC_Witherskeleton.class);
        
        Entity.registerEntity(NPC_Boat.class.getSimpleName(), NPC_Boat.class);
        Entity.registerEntity(NPC_Minecart.class.getSimpleName(), NPC_Minecart.class);
        
        Entity.registerEntity(NPC_Human.class.getSimpleName(), NPC_Human.class);
    }
	
	@Override
	public void onEnable() {
		Server.getInstance().getPluginManager().registerEvents(new EventListener(), this);
		this.registerNPC();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("cnpc")) {
			if(args.length < 1) return false;
			
			if(args[0].equalsIgnoreCase("spawn")) {
				//entity type name
				if(sender.isPlayer()) {
					if(!ENTITY_LIST.contains(args[1])) {
						sender.sendMessage(TextFormat.RED + "�䤣���NPC��������");
					}
					Player creator = (Player) sender;
					Entity npc;
					String name = "";
					
					String typeName = null;
					if(args[2].equalsIgnoreCase("c") || args[2].equalsIgnoreCase("CommandNPC")) typeName = "CommandNPC";
					else if(args[2].equalsIgnoreCase("m") || args[2].equalsIgnoreCase("MessageNPC")) typeName = "MessageNPC";
					else if(args[2].equalsIgnoreCase("t") || args[2].equalsIgnoreCase("TeleportNPC")) typeName = "TeleportNPC";
					else {
						creator.sendMessage(TextFormat.RED + "Unknown NPC type: " + args[2]);
						return true;
					}
					name = String.join(" ", Arrays.copyOfRange(args, 3, args.length)).replaceAll("%n", "\n");
					CompoundTag nbt = CNPC_Entity.createNBT(creator, "NPC_"+args[1], NPCType.forName(typeName), name);
					npc = Entity.createEntity("NPC_"+args[1], creator.chunk, nbt);
					if(!name.equals("")) {
						npc.setNameTag(name);
						npc.setNameTagAlwaysVisible();
					}
					npc.spawnToAll();
					creator.sendMessage(TextFormat.GREEN + name + "�Ыئ��\! ��NPC���N�X��: " + TextFormat.YELLOW + npc.getId() + 
					"\n�п�J" + TextFormat.WHITE + "/cnpc edit " + npc.getId() + TextFormat.GREEN + "�ӳ]�m��NPC�����e");
					return true;
				}else {
					sender.sendMessage(TextFormat.RED + "�Цb�C���ذ��榹���O");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("edit")) {
				if(sender.isPlayer()) {
					if(args.length < 2) {
						sender.sendMessage(TextFormat.RED + "�п�JNPC�N��!");
						return false;
					}
					if(!CJEF.isInteger(args[1])) {
						sender.sendMessage(TextFormat.RED + "�п�J���T��NPC�N��");
						return true;
					}
					long eid = Long.parseLong(args[1]);
					Player p = (Player)sender;
					Level level = p.getLevel();
					Entity npc = level.getEntity(eid);
					if(killerPool.contains(p.getName())) {
						CNPCMain.getNpcEditRequestions().remove(p.getName());
						p.sendMessage(TextFormat.ITALIC + (TextFormat.GRAY + "�w�h�XNPC�s��Ҧ�"));
					}
					if((npc instanceof CNPC_Entity || npc instanceof NPC_Human) && npc.namedTag.getBoolean("Npc")) {
						ListTag<StringTag> typeList = npc.namedTag.getList("NPCType", StringTag.class);
						switch(typeList.get(0).data.toLowerCase()) {
							case "commandnpc":
								p.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "��NPC�����O��NPC(CommandNPC), �Цb��ѫǿ�J: \n" + TextFormat.GREEN
												+ "@sc <lv>     -�]�w��NPC���ؤo\n"
												+ "@n <name>     -�]�w��NPC���W��\n"
												+ "@c <command>  -�]�w��NPC�����O\n"
												+ "@s <name>     -�]�w��NPC�����O�����\n"
												+ "@cancel       -�����s��"));
								break;
							case "messagenpc":
								p.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "��NPC����ܫ�NPC(MessageNPC), �Цb��ѫǿ�J: \n" + TextFormat.GREEN
												+ "@sc <lv>     -�]�w��NPC���ؤo\n"
												+ "@n <name>     -�]�w��NPC���W��\n"
												+ "@m <message>  -�]�w��NPC���T��\n"
												+ "@cancel       -�����s��"));
								break;
							case "teleportnpc":
								p.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "��NPC���ǰe��NPC(MessageNPC), �Цb��ѫǿ�J: \n" + TextFormat.GREEN
												+ "@sc <lv>       -�]�w��NPC���ؤo\n"
												+ "@n <name>       -�]�w��NPC���W��\n"
												+ "@w <world>      -�]�wĲ�o��NPC�ǰe���@��\n"
												+ "@p <x> <y> <z>  -�]�wĲ�o��NPC�ǰe���ؼ�\n"
												+ "@cancel         -�����s��"));
								break;
							default:
								p.sendMessage(TextFormat.RED + "Unkown type: " + typeList.get(0).data);
								return true;
						}
						npcEditRequestions.put(p.getName(), npc);
					}
				}else {
					sender.sendMessage(TextFormat.RED + "�Цb�C���ذ��榹���O");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("kill")) {
				if(sender.isPlayer()) {
					Player p = (Player)sender;
					if(CNPCMain.getKillerPool().contains(p.getName())) {
						CNPCMain.getKillerPool().remove(p.getName());
						p.sendMessage(TextFormat.ITALIC + (TextFormat.GRAY + "�w�h�XNPC�����Ҧ�"));
					}
					if(!killerPool.contains(p.getName())) {
						killerPool.add(p.getName());
						p.sendMessage(TextFormat.ITALIC + (TextFormat.GRAY + "KILLER MODE�Ұ�, ���I��NPC�R��\n"
								+ "�Y�������Цb��ѫǿ�J@cancel"));
					}else
						p.sendMessage(TextFormat.ITALIC + (TextFormat.GRAY + "�A�w�B��KILL MODE���A\n"
								+ "�Y�������Цb��ѫǿ�J@cancel"));
				}else {
					sender.sendMessage(TextFormat.RED + "�Цb�C���ذ��榹���O");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("list")) {
				if(sender.isPlayer()) {
					Player p = (Player)sender;
					Level level = null;
					if(args.length < 2) {
						level = p.getLevel();
					}else {
						level = Server.getInstance().getLevelByName(args[1]);
					}
					if(level != null) {
						p.sendMessage(TextFormat.GRAY + "���b�ǳ�" + level.getFolderName() + "��NPC�C��......");
						String npcList = "";
						String separator = "=====================\n";
						npcList +="-- �s�b��" + TextFormat.DARK_GREEN + level.getName() + TextFormat.WHITE + "��NPC�C�� --\n" + separator;
						for(Entity e : level.getEntities()) {
							if((e instanceof CNPC_Entity || e instanceof NPC_Human) && e.namedTag.getBoolean("Npc")) {
								ListTag<StringTag> typeList = e.namedTag.getList("NPCType", StringTag.class);
								npcList += TextFormat.GREEN + "ID" + e.getId() + ": \n" + TextFormat.RESET + 
											TextFormat.DARK_GREEN + "NPC�W��: " + TextFormat.RESET + e.namedTag.getString("NameTag") + "\n" + 
											TextFormat.DARK_GREEN + "NPC����: " + TextFormat.RESET + typeList.get(0).data + "\n" + 
											TextFormat.DARK_GREEN + "NPC����: " + TextFormat.RESET + e.getClass().getSimpleName().replace("NPC_", "") + "\n";
								
								if(typeList.get(0).data.equalsIgnoreCase("CommandNPC")) {
									npcList += TextFormat.DARK_GREEN + "NPC���O: " + TextFormat.RESET + typeList.get(2).data + "\n" + 
												TextFormat.DARK_GREEN + "NPC���O�����: " + TextFormat.RESET + typeList.get(1).data + "\n";
								}else if(typeList.get(0).data.equalsIgnoreCase("MessageNPC")) {
									npcList += TextFormat.DARK_GREEN + "NPC�T��: " + TextFormat.RESET + typeList.get(1).data + "\n";
								}else if(typeList.get(0).data.equalsIgnoreCase("TeleportNPC")) {
									String levelName = typeList.get(1).data;
									Level to = levelName.equals("")? p.getLevel() : Server.getInstance().getLevelByName(levelName);
									if(to == null) to = p.getLevel();
									String xStr = typeList.get(2).data;
									String yStr = typeList.get(3).data;
									String zStr = typeList.get(4).data;
									Vector3 pos = (xStr.equals("") || yStr.equals("") || zStr.equals(""))? 
											to.getSpawnLocation() : new Vector3(Double.parseDouble(xStr), Double.parseDouble(yStr), Double.parseDouble(zStr));
									npcList += TextFormat.DARK_GREEN + "NPC�ǰe�a��: " + TextFormat.RESET + to.getFolderName() + "\n" + 
											TextFormat.DARK_GREEN + "NPC�ǰe�I: " + TextFormat.RESET + "("+pos.x+", "+pos.y+", "+pos.z+")\n";
								}
								npcList += TextFormat.RESET + separator;
							}
						}
						p.sendMessage(npcList);
					}else {
						p.sendMessage(TextFormat.RED + "�L�Ħa�ϦW");
						return true;
					}
				}else {
					sender.sendMessage(TextFormat.RED + "�Цb�C���ذ��榹���O");
					return true;
				}
			}
		}
		return true;
	}
}
