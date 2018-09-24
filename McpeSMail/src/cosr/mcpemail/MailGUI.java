package cosr.mcpemail;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

public class MailGUI {
	
	private Main plugin;
	
	public MailGUI(Main plugin) {
		this.plugin = plugin;
	}
	
	public void homePage(Player p) {
		FormWindowSimple window = new FormWindowSimple("�H�c����", "");
		
		window.addButton(new ElementButton(TextFormat.BOLD + "�H�H"));
		
		if(plugin.gmailConfig().exists("MailAddress") && plugin.gmailConfig().exists("Password")) {
			window.addButton(new ElementButton(TextFormat.BOLD + "Gmail�H�H"));
		}else {
			if(p.isOp()) window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "�]�wGmail�H�c")));
		}
		
		window.addButton(new ElementButton(TextFormat.BOLD + "�ڪ��H�c"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.RED + "�M�ūH�c")));
		window.addButton(new ElementButton(TextFormat.BOLD + ("��������")));
		
		p.showFormWindow(window);
	}
	
	public void gmailSetW(Player p) {
		FormWindowCustom window = new FormWindowCustom("Gmail�H�c�]�w");
		
		window.addElement(new ElementInput("�ӤH�H�c�a�}", "example@gmail.com"));
		window.addElement(new ElementInput("Server�H�c�a�}", "example@gmail.com"));
		window.addElement(new ElementInput("Server�H�c�K�X"));
		window.addElement(new ElementInput("�H��o�e�ӷ�", "Server(Minecraft PE)"));
		
		p.showFormWindow(window);
	}
	
	public void mailOutW(Player p) {
		FormWindowCustom window = new FormWindowCustom("�H��l�H");
		
		window.addElement(new ElementInput("����H"));
		window.addElement(new ElementInput("�D��"));
		window.addElement(new ElementInput("���e"));
		
		p.showFormWindow(window);
	}
	
	public void mailOutW(Player p, String reciever) {
		FormWindowCustom window = new FormWindowCustom("�H�H�� " + reciever);
		window.addElement(new ElementInput("�п�J�D��"));
		window.addElement(new ElementInput("�п�J���e"));
		
		p.showFormWindow(window);
	}
	
	public void gmailOutW(Player p) {
		FormWindowCustom window = new FormWindowCustom("Gmail�H��l�H");
		
		window.addElement(new ElementInput("����H", "example@gmail.com"));
		window.addElement(new ElementInput("�D��"));
		window.addElement(new ElementInput("���e"));
		
		p.showFormWindow(window);
	}
	
	public void mailListW(Player p) {
		FormWindowSimple window = new FormWindowSimple("�H��C��", "");
		int num = 1;
		for(Mail element : plugin.getMailboxes().get(p.getName()).getMails().values()) {
			window.addButton(new ElementButton(Mail.formMailTitle(num, element)));
			num++;
		}
		window.addButton(new ElementButton(TextFormat.BOLD + "��^����"));
		
		p.showFormWindow(window);
	}
	
	public void mailActionW(Mail mail, Player p) {
		//mailboxes�O�x�s ���a�W-�H�c���� ��Map
		FormWindowSimple window = new FormWindowSimple("�H��#"+plugin.getMailboxes().get(p.getName()).getIDof(mail), "�п�ܱz�Q�n�惡�H����檺�ʧ@");
		
		window.addButton(new ElementButton("�\Ū�H��"));
		window.addButton(new ElementButton("�Хܬ��wŪ"));
		window.addButton(new ElementButton("�Хܬ���Ū"));
		window.addButton(new ElementButton("�Хܬ����n"));
		window.addButton(new ElementButton("�Хܬ������n"));
		window.addButton(new ElementButton(TextFormat.RED + "�R�����H��"));
		window.addButton(new ElementButton(TextFormat.BOLD + "��^�ܦC��"));
		
		p.showFormWindow(window);
	}
	
	public void mailReadingW(Mail mail, Player p) {
		FormWindowModal window = new FormWindowModal("�H�󤺮e", Mail.formMailDetail(mail), "�^�H", "��^");
		p.showFormWindow(window);
	}
	
	public void mailDeleteW(Mail mail, Player p) {
		FormWindowModal window = new FormWindowModal("�T�{�R�����H��?", "�Y�R����N�L�k�_��A�O�_�T�w�R�����H��?\n\n" + Mail.formMailDetail(mail), "�T�{", "����");
		p.showFormWindow(window);
	}
	
	public void mailboxCleareW(Player p) {
		FormWindowModal window = new FormWindowModal("�T�{�M�ūH�c?", "�Y�M�ū�N�L�k�_��A�O�_�T�w�M�űz���H�c?", "�T�{", "����");
		p.showFormWindow(window);
	}
}
