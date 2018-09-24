package cosr.mcpemail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.mail.AuthenticationFailedException;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase {
	
	private HashMap<String, MailBox> mailboxes;		//���a�W-���a�H�c
	private HashMap<String, Mail> oppool;			//���a�W-���a�b���������W�I�����H��
	private Config gmailConf;
	private MailGUI ui;
	private String usage = "/mail send [player_name] [subject] [content]   �H�e�@�ʹC���H�󵹪��a\n"
						 + "/mail read [mailID]                            �\Ū�ӽs�����H��\n"
						 + "/mail del [mailID]                             �R���ӽs���H��\n"
						 + "/mail clear                                    �M�ūH�c\n"
						 + "/mail ui                                       �ϥΫH�c����\n"
						 + "/mail gset tm [Example@gmail.com]              �]�m���եΫH�c\n"
						 + "/mail gset addr [Example@gmail.com]            �]�wgmail�H�c��}\n"
						 + "/mail gset pw [password]                       �]�wgmail�H�c�K�X\n"
						 + "/mail gset src [mail_source]                   �]�wgmail�H�H�ӷ�";
	
	private static Main plugin = null;
	
	public static Main getInstance() {
		return Main.plugin;
	}
	
	public void onEnable() {
		plugin = this;
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		this.getDataFolder().mkdirs();
		mailboxes = new HashMap<String, MailBox>();
		oppool = new HashMap<String, Mail>();
		ui = new MailGUI(this);
		
	    gmailConf = new Config(new File(this.getDataFolder(), "GmailData/GmailServiceData.yml"), Config.YAML);
	    if(gmailConf.exists("MailAddress") && gmailConf.exists("Password")) {
			McpeGmail.setPublicSender(gmailConf.getString("MailAddress"), gmailConf.getString("Password"));
			McpeGmail.init();
		}else {
			this.getLogger().info(TextFormat.RED + "Gmail�H�c�|���]�m�����A�Y�]�m������N�L�k�ϥ�Gmail�H��\��C");
		}
	    if(gmailConf.exists("Source")) McpeGmail.setMailSource(gmailConf.getString("Source"));
	    
	    this.getLogger().info(TextFormat.GREEN + "Loaded Done!");
	}
	
	public void onDisable() {
		this.getLogger().info(TextFormat.GRAY + "���b�x�s���a�H�c...");
		for(MailBox box : mailboxes.values()) {
			try {
				box.clearConfig();
				box.saveAll();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.getLogger().info(TextFormat.GRAY + "���a�H�c�x�s����!");
		
		this.getLogger().info(TextFormat.GRAY + "���b�x�sGmail�t�m��...");
		gmailConf.save();
		this.getLogger().info(TextFormat.GRAY + "Gmail�t�m���x�s����!");
		
		this.getLogger().info(TextFormat.BOLD + (TextFormat.DARK_GREEN + "GoodBye!"));
		return;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if(cmd.getName().equals("mail")) {
				//�i�b�C���α���x�����檺���O
				if(args[0].equals("gset")) {
					if(sender.isOp()) {
						if(args[1].equals("tm")) {
							gmailConf.set("TestAddress", args[2]);
							sender.sendMessage(TextFormat.GREEN + "�ӤH�H�c��}�]�w���\!");
						}
						else if(args[1].equals("addr")) {
							if(!gmailConf.exists("TestAddress")) {
								sender.sendMessage(TextFormat.RED + "�Х��]�m�n���եΫH�c ��k: /mail gset tm example@gmail.com");
							}
							if(args[2].endsWith("@gmail.com")) {
								gmailConf.set("MailAddress", args[2]);
								sender.sendMessage(TextFormat.GREEN + "�H�c��}�]�w���\!");
							}else {
								sender.sendMessage(TextFormat.RED + "�H�c�榡���~! �ȭ��ϥΤ@��Gmail�H�c�b��");
							}
						}
						else if(args[1].equals("pw")) {
							gmailConf.set("Password", args[2]);
							sender.sendMessage(TextFormat.GREEN + "�H�c�K�X�]�w���\!");
						}
						else if(args[1].equals("src")) {
							gmailConf.set("Source", args[2]);
							McpeGmail.setMailSource(args[2]);
							sender.sendMessage(TextFormat.GREEN + "�H��ӷ��a�}�]�w���\!");
						}
						else return false;
						gmailConf.save();
					
						if(gmailConf.exists("MailAddress") && gmailConf.exists("Password")) {
							McpeGmail.setPublicSender(gmailConf.getString("MailAddress"), gmailConf.getString("Password"));
							McpeGmail.init();
							if(gmailConf.exists("TestAddress")) {
								McpeGmail testmail = new McpeGmail(sender.getName(), gmailConf.getString("TestAddress"), "The Gmail Sending Test of McpeSMail", "Just Test :)");
								try {
									sender.sendMessage(TextFormat.GRAY + "���b����Gmail���� �Ф����}�C��......");
									testmail.sendOut();
									sender.sendMessage(TextFormat.GREEN + "Gmail���ճq�L!");
								} catch (AuthenticationFailedException e) {
									if(sender.isOp()) sender.sendMessage(TextFormat.RED + "���A���H�c�b���αK�X���~! �Э��s�]�w");
								}
							}
						}
						return true;
					}else sender.sendMessage(TextFormat.RED + "�������O/" + cmd.getName() + "\n�Ϊ̱z�������v�����榹���O");	//end of 'if(sender.isOp())' �p�G���a�D�޲z���h�L�k�ϥ�gset���O
				}
				
				if(args[0].equals("help")) {
					sender.sendMessage(usage);
					return true;
				}
				
				//����즹�N��ϥΪ̿�J�����O�Dgset�A�H�U���O�h�ݭn�b�C�����~�����
				if(!sender.isPlayer()) {
					sender.sendMessage(TextFormat.RED + "�Цb�C�������榹���O");
					return true;
				}
				
				if(args[0].equals("list") || args[0].equals("l")) {
					sender.sendMessage((TextFormat.BOLD + (TextFormat.GREEN + "�ڪ��H�c: \n"))
										+ TextFormat.RESET + mailboxes.get( ((Player)sender).getName() ).listMailsOut());
				}
				else if(args[0].equals("send") || args[0].equals("s")) {
					if(args[1] != null && args[2] != null && args[3] != null) {
						Mail mail = new Mail(sender.getName(), args[1], args[2], args[3]);
						mail.sendOut();
						sender.sendMessage(TextFormat.GREEN + "�H��H�e���\!");
						if(this.getServer().getPlayer(mail.getReciever()) != null)
							this.getServer().getPlayer(mail.getReciever()).sendMessage(TextFormat.GOLD + "���@�ʨӦ�" + sender.getName() + "���H�H���z�F�A�Ьd��!");
					}
				}
				else if(args[0].equals("read") || args[0].equals("r")) {
					if(args[1] != null) {
						if(mailboxes.get(sender.getName()).getMails().containsKey(Integer.parseInt(args[1])))
							mailboxes.get(sender.getName()).getMailbyID(Integer.parseInt(args[1])).readOut();
					}
				}
				else if(args[0].equals("del") || args[0].equals("d")) {
					if(args[1] != null) {
						if(mailboxes.get(sender.getName()).getMails().containsKey(Integer.parseInt(args[1]))) {
							mailboxes.get(sender.getName()).delete(Integer.parseInt(args[1]));
							sender.sendMessage(TextFormat.GRAY + "�H��R�����\!");
						}
					}
				}
				else if(args[0].equals("clear") || args[0].equals("c")) {
					mailboxes.get(sender.getName()).clear();
					sender.sendMessage(TextFormat.GRAY + "�z���H�c�w�M��!");
				}
				else if(args[0].equals("ui") || args[0].equals("u")) {
					ui.homePage((Player) sender);
				}
				else return false;
				
				return true;
			}
		}catch(IOException err) {
			sender.sendMessage(TextFormat.RED + "��p�A�䤣��z���H�c�t�m��!�ХߧY�V���A���޲z������");
			this.getLogger().alert(TextFormat.RED + "�䤣�쪱�a"+sender.getName()+"���H�c�t�m��");
		}catch(ArrayIndexOutOfBoundsException err) {
			sender.sendMessage(TextFormat.RED + "�п�J���T�����O�榡!");
		}
		return false;
	}

	public Config gmailConfig() {
		return gmailConf;
	}

	public HashMap<String, MailBox> getMailboxes() {
		return mailboxes;
	}

	public HashMap<String, Mail> getOppool() {
		return oppool;
	}
	
	public MailGUI mailGUI() {
		return ui;
	}
}
