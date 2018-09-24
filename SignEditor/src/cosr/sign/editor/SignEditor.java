package cosr.sign.editor;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class SignEditor extends PluginBase implements Listener {
	
	private static Map<String, BlockEntitySign> edittingMap = new HashMap<String, BlockEntitySign>();

	public void onEnable() {
		Server.getInstance().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("se")) {
			if(sender.isPlayer() && sender.isOp()) {
				Player p = (Player) sender;
				Item seTool = Item.get(294);
				seTool.setCustomName(TextFormat.ITALIC + (TextFormat.LIGHT_PURPLE + "��r�]��"));
				if(p.getInventory().canAddItem(seTool)) {
					p.getInventory().addItem(seTool);
					p.sendMessage(TextFormat.GREEN + "�z��o�F" + seTool.getName() + TextFormat.RESET + "x1");
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void onTouch(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Item item = event.getItem();
		Block block = event.getBlock();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && 
				item.getId() == 294 && item.getName().equals(TextFormat.ITALIC + (TextFormat.LIGHT_PURPLE + "��r�]��"))) {
			if(block.getId() == 63 || block.getId() == 68) {
				edittingMap.put(p.getName(), (BlockEntitySign) block.getLevel().getBlockEntity(block));
				showRowSelector(p);
			}
		}
	}
	
	private static void showRowSelector(Player p) {
		FormWindowCustom window = new FormWindowCustom("�i�ܵP��r���F");
		ElementDropdown list = new ElementDropdown("�п�ܭn��諸���");
		list.addOption("�Ĥ@��", true);
		list.addOption("�ĤG��");
		list.addOption("�ĤT��");
		list.addOption("�ĥ|��");
		window.addElement(list);
		p.showFormWindow(window);
	}
	
	private static void showTextEditor(Player p, String rowName) {
		FormWindowCustom window = new FormWindowCustom("�i�ܵP��r���F-" + rowName);
		int row = 0;
		switch(rowName) {
		case "�Ĥ@��":
			row = 0;
			break;
		case "�ĤG��":
			row = 1;
			break;
		case "�ĤT��":
			row = 2;
			break;
		case "�ĥ|��":
			row = 3;
			break;
		}
		window.addElement(new ElementInput("�п�J�r��", "", edittingMap.get(p.getName()).getText()[row]));
		p.showFormWindow(window);
	}
	
	@EventHandler
	public void onForm(PlayerFormRespondedEvent event) {
		Player p = event.getPlayer();
		FormWindow window = event.getWindow();
		FormResponse response = event.getResponse();
		
		if(response == null || window == null) {
			edittingMap.remove(p.getName());
			return;
		}
		
		if(window instanceof FormWindowCustom) {
			FormWindowCustom w = (FormWindowCustom) window;
			FormResponseCustom r = (FormResponseCustom) response;
			if(w.getTitle().equals("�i�ܵP��r���F")) {
				if(edittingMap.containsKey(p.getName())) {
					String row = r.getDropdownResponse(0).getElementContent();
					showTextEditor(p, row);
				}
			}
			else if(w.getTitle().startsWith("�i�ܵP��r���F-")) {
				String rowName = w.getTitle().replace("�i�ܵP��r���F-", "");
				String text = r.getInputResponse(0);
				BlockEntitySign sign = edittingMap.get(p.getName());
				if(sign != null && !sign.closed) {
					String[] lines = sign.getText();
					switch(rowName) {
					case "�Ĥ@��":
						sign.setText(text, lines.length >= 2? lines[1]:"", lines.length >= 3? lines[2]:"", lines.length >= 4? lines[3]:"");
						break;
					case "�ĤG��":
						sign.setText(lines.length >= 1? lines[0]:"", text, lines.length >= 3? lines[2]:"", lines.length >= 4? lines[3]:"");
						break;
					case "�ĤT��":
						sign.setText( lines.length >= 1? lines[0]:"", lines.length >= 2? lines[1]:"", text, lines.length >= 4? lines[3]:"");
						break;
					case "�ĥ|��":
						sign.setText(lines.length >= 1? lines[0]:"", lines.length >= 2? lines[1]:"", lines.length >= 3? lines[2]:"", text);
						break;
					default: 
						break;
					}
					edittingMap.remove(p.getName());
				}
			}
		}
	}
}
