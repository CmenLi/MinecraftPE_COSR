package cosr.mcpemail;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

import javax.mail.AuthenticationFailedException;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

public class EventListener implements Listener {
	
	private Main plugin = Main.getInstance();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws EOFException {
		Player player = event.getPlayer();
		File file = new File(plugin.getDataFolder(), player.getName()+".yml");
		MailBox mailbox = new MailBox(player.getName());
		plugin.getMailboxes().put(player.getName(), mailbox);
		int amount = 0;
		try {
		if(file.exists()) mailbox.readAll();
		else {
			Mail guidemail = new Mail("�Q�ɺ��F", player.getName(), "�w��z�����", "���o! �A�O�Ĥ@���[�J���A����a?�ХJ�Ӿ\Ū���s�������A�}�l�C��!");
			guidemail.sendOut();
		}
		
		for(Mail mail : plugin.getMailboxes().get(player.getName()).getMails().values()) {
			if(!mail.isRead()) amount++;
		}
		
		if(amount != 0) player.sendMessage(TextFormat.GREEN + "�z�|��"+Integer.toString(amount)+"�s�ʫH��Ū");
		
		}catch(Exception e) {}
	}
		
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(plugin.getMailboxes().containsKey(player.getName())) {
			MailBox mb = plugin.getMailboxes().get(player.getName());
			try {
				mb.clearConfig();
				mb.saveAll();
				plugin.getMailboxes().remove(player.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void onResponse(PlayerFormRespondedEvent event) {
		FormWindow window = event.getWindow();
		FormResponse response = event.getResponse();
		Player player = event.getPlayer();
		String title, button, pre_title = "";
		try {
			if(window instanceof FormWindowSimple) {
				title = ((FormWindowSimple) window).getTitle();
				button = ((FormResponseSimple) response).getClickedButton().getText();
				if(title.equals("�H�c����")) {
					if(button.equals(TextFormat.BOLD + "�H�H")) plugin.mailGUI().mailOutW(player);
					if(button.equals(TextFormat.BOLD + (TextFormat.GRAY + "�]�wGmail�H�c"))) plugin.mailGUI().gmailSetW(player);
					if(button.equals(TextFormat.BOLD + "Gmail�H�H")) plugin.mailGUI().gmailOutW(player);
					if(button.equals(TextFormat.BOLD + "�ڪ��H�c")) plugin.mailGUI().mailListW(player);
					if(button.equals(TextFormat.BOLD + (TextFormat.RED + "�M�ūH�c"))) plugin.mailGUI().mailboxCleareW(player);
				}
				//�p�G���a�b�H��C���I���FA�H��A�N�� ���a-A�H�� ��Joppool
				if(title.equals("�H��C��")) {
					int num = 1;
					for(Mail mail : plugin.getMailboxes().get(player.getName()).getMails().values()) {
						if(button.equals(Mail.formMailTitle(num, mail))) {
							plugin.getOppool().put(player.getName(), mail);
							plugin.mailGUI().mailActionW(mail, player);
							break;
						}
						num++;
					}
					if(button.equals(TextFormat.BOLD + "��^����")) plugin.mailGUI().homePage(player);
				}
				//mailActionWindow�����D�ѫe�@�ӵ���(mailListWindow)���I�����H��M�w
				if(plugin.getOppool().containsKey(player.getName())) pre_title = "�H��#"+plugin.getMailboxes().get(player.getName()).getIDof(plugin.getOppool().get(player.getName()));
				if(title.equals(pre_title)) {
					if(button.equals("�\Ū�H��")) plugin.mailGUI().mailReadingW(plugin.getOppool().get(player.getName()), player);
					if(button.equals("�Хܬ��wŪ")) {
						player.sendMessage(TextFormat.GREEN + (pre_title + "�w�Q�]���wŪ"));
						plugin.getOppool().get(player.getName()).toRead();
						plugin.mailGUI().mailListW(player);
					}
					if(button.equals("�Хܬ���Ū")) {
						player.sendMessage(TextFormat.GREEN + (pre_title + "�w�Q�]����Ū"));
						plugin.getOppool().get(player.getName()).unRead();
						plugin.mailGUI().mailListW(player);
					}
					//if(button.equals("�Хܬ����n"))		�ݧ�s
					//if(button.equals("�Хܬ������n"))		�ݧ�s
					if(button.equals(TextFormat.RED + "�R�����H��")) plugin.mailGUI().mailDeleteW(plugin.getOppool().get(player.getName()), player);
					if(button.equals(TextFormat.BOLD + "��^�ܦC��")) plugin.mailGUI().mailListW(player);
				}
			}
			else if(window instanceof FormWindowCustom) {
				title = ((FormWindowCustom) window).getTitle();
				
				if(title.equals("Gmail�H�c�]�w")) {
					String prvt = ((FormResponseCustom) response).getInputResponse(0);
					String address = ((FormResponseCustom) response).getInputResponse(1);
					String password = ((FormResponseCustom) response).getInputResponse(2);
					String source = ((FormResponseCustom) response).getInputResponse(3);
					
					if(prvt.equals("") || !prvt.contains("@")) {
						player.sendMessage(TextFormat.RED + "�ӤH�H�c�b���榡���~! �Э��s��J");
						return;
					}else {
						plugin.gmailConfig().set("TestAddress", prvt);
					}
					
					if(!address.equals("") && !password.equals("")) {
						if(address.endsWith("@gmail.com")) {
							plugin.gmailConfig().set("MailAddress", address);
						}else {
							player.sendMessage(TextFormat.RED + "�H�c�榡���~! �ȭ��ϥΤ@��Gmail�H�c�b��");
							plugin.mailGUI().gmailSetW(player);
						}
						plugin.gmailConfig().set("Password", password);
						if(!source.equals("")) {
							plugin.gmailConfig().set("Source", source);
							McpeGmail.setMailSource(source);
						}
						
						plugin.gmailConfig().save();
						if(plugin.gmailConfig().exists("MailAddress") && plugin.gmailConfig().exists("Password")) {
							McpeGmail.setPublicSender(plugin.gmailConfig().getString("MailAddress"), plugin.gmailConfig().getString("Password"));
							McpeGmail.init();
							McpeGmail testmail = new McpeGmail(player.getName(), prvt, "The Gmail Sending Test of McpeSMail", "Just Test :)");
							try {
								player.sendMessage(TextFormat.GRAY + "���b����Gmail���� �Ф����}�C��......");
								testmail.sendOut();
								player.sendMessage(TextFormat.GREEN + "Gmail���ճq�L!");
								player.sendMessage(TextFormat.GREEN + "Gmail�H�c�]�w���\! �i�H�}�l�ϥ�Gmail�H��\��F");
							} catch (AuthenticationFailedException e) {
								if(player.isOp()) {
									player.sendMessage(TextFormat.RED + "�H�c�b���αK�X���~! �Э��s�]�w");
									plugin.mailGUI().gmailSetW(player);
								}
							}
						}
					}else {
						player.sendMessage(TextFormat.RED + "�H�c�b���H�αK�X���������!");
						plugin.mailGUI().gmailSetW(player);
					}
				}
			
				if(title.equals("�H��l�H")) {
					String reciever, topic, content = "";
					reciever = ((FormResponseCustom) response).getInputResponse(0);
					topic = ((FormResponseCustom) response).getInputResponse(1);
					content = ((FormResponseCustom) response).getInputResponse(2);
					Mail newmail = new Mail(player.getName(), reciever, topic, content);
					newmail.sendOut();
					player.sendMessage(TextFormat.GREEN + "�H��w���\�H�X");
					if(plugin.getServer().getPlayer(newmail.getReciever()) != null)
						plugin.getServer().getPlayer(newmail.getReciever()).sendMessage(TextFormat.GOLD + "���@�ʨӦ�" + player.getName() + "���H�H���z�F�A�Ьd��!");
				}
				
				if(title.equals("Gmail�H��l�H")) {
					try {
						String recipient, subtitle, content = "";
						recipient = ((FormResponseCustom) response).getInputResponse(0);
						subtitle = ((FormResponseCustom) response).getInputResponse(1);
						content = ((FormResponseCustom) response).getInputResponse(2);
						McpeGmail gmail = new McpeGmail(player.getName(), recipient, subtitle, content);
						player.sendMessage(TextFormat.GRAY + "���b���ձH�eGmail�H��...");
						gmail.sendOut();
						player.sendMessage(TextFormat.GREEN + "Gmail�H��H�e���\!");
					}catch(AuthenticationFailedException err) {
						player.sendMessage(TextFormat.RED + "Gmail�H��o�e����! ��]����");
						plugin.getServer().getLogger().alert(err.getMessage());
					}
				}
			}
			else if(window instanceof FormWindowModal) {
				title = ((FormWindowModal) window).getTitle();
				if(title.equals("�H�󤺮e")) {
					if(plugin.getOppool().containsKey(player.getName())) plugin.getOppool().get(player.getName()).toRead();
				
					if(((FormResponseModal) response).getClickedButtonText().equals("�^�H")) {
						plugin.mailGUI().mailOutW(player);
					}
					else plugin.mailGUI().mailListW(player);
				}
				if(title.equals("�T�{�R�����H��?")) {
					if(((FormResponseModal) response).getClickedButtonText().equals("�T�{")) {
						if(plugin.getOppool().containsKey(player.getName())) {
							try {
								//��ӫH��q���a���H�c������
								plugin.getMailboxes().get(player.getName()).delete(plugin.getMailboxes().get(player.getName()).getIDof(plugin.getOppool().get(player.getName())));
							} catch (IOException e) {
								e.printStackTrace();
							}
							plugin.getOppool().remove(player.getName());
							player.sendMessage(TextFormat.GREEN + (pre_title + "�w���\�Q�R��"));
						}else player.sendMessage(TextFormat.RED + "�R������!(��]:�H�c���䤣��ӫH��)");
					}
					plugin.mailGUI().mailListW(player);
				}
				//�����b�������I�����\��A�L���ާ@oppool
				if(title.equals("�T�{�M�ūH�c?")) {
					if(((FormResponseModal) response).getClickedButtonText().equals("�T�{")) {
						try {
							plugin.getMailboxes().get(player.getName()).clear();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else 
						plugin.mailGUI().homePage(player);
				}
			}
		}catch(NullPointerException err) {
			//Just catch
		}
	}
}
