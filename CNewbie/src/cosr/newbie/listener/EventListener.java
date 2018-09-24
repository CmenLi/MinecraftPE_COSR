package cosr.newbie.listener;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockChest;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import cosr.newbie.BuildTool;
import cosr.newbie.CNewbie;
import cosr.newbie.chest.NewbieChest;
import cosr.newbie.event.BuffSetter;
import cosr.newbie.event.MobSpawner;
import cosr.newbie.event.SpawnZone;
import cosr.roleplay.CRolePlay;
import cosr.roleplay.database.PlayerDataBase;

public class EventListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(CNewbie.allDone()) {
			Player p = event.getPlayer();
			PlayerDataBase pdb = null;
			if(CRolePlay.getOnlinePDB().containsKey(p.getName())) {
				pdb = CRolePlay.getOnlinePDB().get(p.getName());
			}else {
				pdb = new PlayerDataBase(p.getName());
			}
			if(pdb.isNewPlayer || !CNewbie.isPassed(p)) {
				p.teleport(CNewbie.NBVillage.getSpawnLocation());
				p.sendMessage("[" + TextFormat.GREEN + "���R" + TextFormat.RESET + "]" + TextFormat.ITALIC + (TextFormat.GOLD
						+ "�x? �O�ͭ��թO...... �z���G�O�Ĥ@����ӳo�ӥ@�ɪ���a?\n"
						+ "�n�a, �����F�N���a�A���x�@�U�n�F<3"));
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(CNewbie.allDone()) {
			Player p = event.getPlayer();
			//Position from = event.getFrom();
			//Position pos = event.getTo();
			if(CNewbie.getBuildingMap().containsKey(p.getName()) || CNewbie.isPassed(p)) {
				return;
			}
			for(MobSpawner ms : CNewbie.MSPool) {
				if(ms.checkTrigger(p)) {
					ms.spawnAllMobs();
					ms.savePlayedOne(p);
				}
			}
			for(BuffSetter bs : CNewbie.BSPool) {
				if(bs.checkTrigger(p)) {
					bs.apply(p);
					bs.savePlayedOne(p);
				}
			}
			if(CNewbie.checkPass(p)) {
				CNewbie.pass(p);
			}
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(CNewbie.allDone()) {
			Player p = event.getPlayer();
			if(event.getFrom().getLevel().equals(CNewbie.NBVillage) && !event.getTo().getLevel().equals(CNewbie.NBVillage)) {
				if(CRolePlay.getOnlinePDB().getOrDefault(p.getName(), new PlayerDataBase(p.getName())).isNewPlayer || !CNewbie.isPassed(p)) {
					event.setCancelled();
					p.sendMessage(CNewbie.TITLE + TextFormat.RED + "�b�����s��оǤ��e �L�k���}���a");
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		Block b = event.getBlock();
		if(b.getId() == Block.CHEST) {
			for(NewbieChest nbc : CNewbie.getNBChestList()) {
				if(b.getLocation().equals(nbc.getChest().getLocation())) {
					if(p.isOp()) {
						nbc.setChest(null);
						p.sendMessage(TextFormat.GRAY + "�s��c�w�Q�}�a, �Э��s�]�m");
					}else {
						p.sendPopup(TextFormat.RED + "�A�S���v���}�a�ӷs��c");
						event.setCancelled();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onChestOpen(InventoryOpenEvent event) {
		Player p = event.getPlayer();
		for(NewbieChest nbc : CNewbie.getNBChestList()) {
			if(event.getInventory().getHolder().equals(nbc.getChestEntity())) {
				if(p.isOp() && CNewbie.getNbChestContentingPool().contains(p.getName())) {
					nbc.replenish();
					break;
				}
			}
		}
	}
	
	@EventHandler
	public void onChestClose(InventoryCloseEvent event) {
		Player p = event.getPlayer();
		for(NewbieChest nbc : CNewbie.getNBChestList()) {
			if(event.getInventory().getHolder().equals(nbc.getChestEntity())) {
				if(p.isOp() && CNewbie.getNbChestContentingPool().contains(p.getName())) {
					nbc.getContent().clear();
					Map<Integer, Item> contents = event.getInventory().getContents();
					for(Integer index : contents.keySet()) {
						if(contents.get(index).getId() != Item.AIR) {
							nbc.addItem(index, contents.get(index));
						}
					}
					p.sendMessage(CNewbie.TITLE + TextFormat.GREEN + "�s����y�c���e�t�m����!!\n" + 
									TextFormat.RESET + "Size: " + contents.size());
					CNewbie.getNbChestContentingPool().remove(p.getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onTouch(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Block b = event.getBlock();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getFace().equals(BlockFace.UP)) {
			if(CNewbie.getBuildingMap().containsKey(p.getName())) {
				BuildTool bt = CNewbie.getBuildingMap().get(p.getName());
				Position pos = b.getLocation().add(BlockFace.UP.getUnitVector());
				if(bt.step == 1) {
					if(bt.getEffectType().equals("MobSpawn")) {
						bt.getTempMobSpawner().getDetectZone().add(pos);
					}else if(bt.getEffectType().equals("EffectSet")) {
						bt.getTempBuffSetter().getDetectZone().add(pos);
					}
				}
				else if(bt.step == 3) {
					if(bt.getEffectType().equalsIgnoreCase("MobSpawn")) {
						SpawnZone sz = new SpawnZone(pos);
						if(!bt.tempZones.contains(sz)) bt.tempZones.add(sz);
						bt.showMobSpawnSetter();
					}
				}
			}
			if(CNewbie.getEndSettingPool().contains(p.getName())) {
				CNewbie.endPoint = b.add(new Vector3(0, 1, 0));
				p.sendMessage(CNewbie.TITLE + TextFormat.GREEN + "���\�]�m���I");
				CNewbie.getEndSettingPool().remove(p.getName());
			}
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(CNewbie.getNbChestSettingPool().contains(p.getName())) {
				if(b.getId() == Block.CHEST) {
					event.setCancelled();
					NewbieChest nbc = new NewbieChest((BlockChest) b);
					nbc.replenish();
					CNewbie.getNBChestList().add(nbc);
					p.sendMessage(CNewbie.TITLE + TextFormat.GREEN + "�s��c�]�m���\!!");
					CNewbie.getNbChestSettingPool().remove(p.getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event) {
		Player p = event.getPlayer();
		String msg = event.getMessage();
		BuildTool bt = CNewbie.getBuildingMap().get(p.getName());
		if(bt == null) return;
		if(msg.equalsIgnoreCase("@next")) {
			if(bt != null) {
				if(bt.step == 1) {
					event.setCancelled();
					bt.step++;
					bt.prompt();
					bt.showDetectTypeSetter();
				}
			}
		}else if(msg.equalsIgnoreCase("@done")) {
			if(bt.step == 3) {
				event.setCancelled();
				CNewbie.MSPool.add(bt.getTempMobSpawner());
				bt.step = 0;
				CNewbie.getBuildingMap().remove(p.getName());
				p.sendMessage(CNewbie.TITLE + TextFormat.GREEN + "�ͪ��ͦ��}���]�w����!");
			}
		}
	}
	
	@EventHandler
	public void onForm(PlayerFormRespondedEvent event) {
		Player p = event.getPlayer();
		FormWindow window = event.getWindow();
		FormResponse response = event.getResponse();
		
		if(response == null) {
			if(CNewbie.getBuildingMap().containsKey(p.getName())) CNewbie.getBuildingMap().remove(p.getName());
			return;
		}
		
		if(CNewbie.getBuildingMap().containsKey(p.getName())) {
			BuildTool bt = CNewbie.getBuildingMap().get(p.getName());
			if(window instanceof FormWindowSimple) {
				FormResponseSimple r = (FormResponseSimple) response;
				String btxt = r.getClickedButton().getText();
				if(((FormWindowSimple) window).getTitle().equals("�п�ܮĪG")) {
					bt.onEffectSelect(btxt.split(" ")[1]);
					bt.prompt();
				}
			}else if(window instanceof FormWindowCustom) {
				FormResponseCustom r = (FormResponseCustom)response;
				if(((FormWindowCustom) window).getTitle().equals("�]�w������������")) {
					if(bt.step == 2) {
						bt.step++;
						if(bt.getEffectType().equals("MobSpawn")) {
							bt.getTempMobSpawner().setTriggerType(r.getInputResponse(0));
						}else if(bt.getEffectType().equals("EffectSet")) {
							bt.getTempBuffSetter().setTriggerType(r.getInputResponse(0));
							bt.showBuffSetter();
						}
						bt.prompt();
					}
				} else if(((FormWindowCustom) window).getTitle().equals("�]�w�ͪ������Υͦ��q")) {
					if(bt.step == 3) {
						bt.onMobSpawnSetting(r);
					}
				} else if(((FormWindowCustom) window).getTitle().equals("�]�w�t��")) {
					if(bt.step == 3) {
						CNewbie.getBuildingMap().remove(p.getName());
					}
				} else if(((FormWindowCustom) window).getTitle().equals("�]�w���A�ĪG")) {
					if(bt.step == 3) {
						bt.onBuffSetting(r);
						CNewbie.getBuildingMap().remove(p.getName());
					}
				}
			}
		}
	}
}
