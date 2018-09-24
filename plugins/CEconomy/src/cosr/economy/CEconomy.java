package cosr.economy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmen.essalg.CJEF;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cosr.economy.bank.CBank;
import cosr.economy.job.CJob;
import cosr.economy.job.Job;
import cosr.economy.job.JobRecorder;
import cosr.economy.listener.EventListener;
import cosr.economy.listener.GuiEventListener;
import cosr.economy.task.BankUpdator;

public class CEconomy extends PluginBase {
	
	public static final String PDBPATH = "PlayerDataBase" + File.separator;
	
	//�C�Ӫ��a���u�@
	private static Map<String, CJob> player_Job = new HashMap<String, CJob>();
	
	private static CEconomy main = null;
	
	public static CEconomy getInstance() {
		return main;
	}

	public static Map<String, CJob> getJobMap() {
		return player_Job;
	}

	//TODO: onEnable()
	@Override
	public void onEnable() {
		main = this;
		
		CBank.loadData();
		
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		this.getServer().getPluginManager().registerEvents(new GuiEventListener(), this);
		
		//����reloadŪ������w�b�u���a�������B�I��B�u�@
		if(this.getServer().getOnlinePlayers().size() > 0) {
			//TODO: �������J�g���@��method
			for(Player player : this.getServer().getOnlinePlayers().values()) {
				File playerDataFile = new File(CEconomy.getInstance().getDataFolder(), CEconomy.PDBPATH+(player.getName()+".yml"));
				Config playerDataConfig = new Config(playerDataFile, Config.YAML);
				
				if(!playerDataConfig.exists("Money")) playerDataConfig.set("Money", 200.0);
				if(!playerDataConfig.exists("Point")) playerDataConfig.set("Point", 0.0);
				if(!playerDataConfig.exists("Job")) playerDataConfig.set("Job", "None");
				
				playerDataConfig.save();
				
				CMoney.getMoneyMap().put(player.getName(), (float)playerDataConfig.getDouble("Money"));
				CPoint.getPointMap().put(player.getName(), (float)playerDataConfig.getDouble("Point"));
				player_Job.put(player.getName(), new CJob(Job.getJob(playerDataConfig.getString("Job", "None")), new JobRecorder()));
			}
		}
		
		//��l��CPoint�զW��
		Config wlConf = new Config(new File(this.getDataFolder(), CPoint.FILEPATH + "whitelist.yml"), Config.YAML);
		for(String member : wlConf.getStringList("whitelist")) {
			CPoint.registWL(member);
		}
		wlConf = null;
		
		//�Ȧ�C���s
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int dateDiff = CJEF.getDateDifferent(now, CBank.getLastUpdateDate());
		this.getServer().getScheduler().scheduleDelayedRepeatingTask(new BankUpdator(this), 1728000, 1728000);
		if(dateDiff != 0) {
			for(int i = 1; i <= dateDiff; i++) {
				for(String p : CBank.getDepositMap().keySet()) {
					CBank.getDepositMap().put(p, CEconomy.getD2((CBank.getDepositMap().get(p) * (1 + CBank.getInterestRate()))));
				}
				for(String p : CBank.getLoanMap().keySet()) {
					CBank.getLoanMap().get(p).dayIncreasing();
				}
			}
		}
	}

	//TODO: onDisable()
	/*
	 * ���A�������ɱN�x�s�Ҧ���Ʀ��ɮפ�</br>
	 * 
	 * @see cn.nukkit.plugin.PluginBase#onDisable()
	 */
	@Override
	public void onDisable() {
		//�x�s���a����
		for(String p : CMoney.getMoneyMap().keySet()) {
			Config conf = new Config(new File(this.getDataFolder(), PDBPATH + p+".yml"), Config.YAML);
			conf.set("Money", CMoney.getMoneyMap().get(p));
			
			conf.save();
		}
		
		//�x�s���a�I��
		for(String p : CPoint.getPointMap().keySet()) {
			Config conf = new Config(new File(this.getDataFolder(), PDBPATH + p+".yml"), Config.YAML);
			conf.set("Point", CPoint.getPointMap().get(p));
			
			conf.save();
		}
		
		//�x�s���a�u�@
		for(String p : player_Job.keySet()) {
			Config conf = new Config(new File(this.getDataFolder(), PDBPATH + p+".yml"), Config.YAML);
			conf.set("Job", player_Job.get(p).getJob().getName());
			
			conf.save();
		}
		
		//�x�s�Ȧ檬�A
		CBank.saveData();
		
		//�x�sCPoint�զW��
		Config wlConf = new Config(new File(this.getDataFolder(), CPoint.FILEPATH + "whitelist.yml"), Config.YAML);
		wlConf.set("whitelist", CPoint.whiteList);
		wlConf.save();
		wlConf = null;
	}

	//TODO: onCommand()
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch(cmd.getName()) {
			case "cec":
				if(args.length < 1) {
					return false;
				}
				
				if(args[0].equals("c$")) {
					
				}
				else if(args[0].equals("cshop")) {
					
				}
				else if(args[0].equals("cbank")) {
					
				}
				else if(args[0].equals("cjob")) {
					
				}
				else if(args[0].equals("ui")) {
					if(sender.isPlayer()) {
						Player p = (Player)sender;
						p.showFormWindow(EconomyGUI.homePage());
					}
				}
				else {
					return false;
				}
		
			case "c$":
				if(args.length < 1) return false;
				
				if(args[0].equals("help") || args[0].equals("h")) {
					sender.sendMessage(PluginInfo.moneyHelp());
				}
				else if(args[0].equals("give")) {
					if(args.length >= 3) {
						if(!isDigit(args[2])) {
							sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ʀr�榡���~! ���ˬd�᭫�s��J!");
							return true;
						}
						try {
							float money = (float) Double.parseDouble(args[2]);
							if(sender.isPlayer()) {
								CMoney.giveMoney(sender.getName(), args[1], money);
							}else {
								CMoney.giveMoney(args[1], money);
							}
							sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.GREEN + "���\����" + args[1] + "���a" + args[2] + "������!");
						}catch(FileNotFoundException err) {
							sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�䤣��Ӫ��a");
						}
					}else 
						sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ы��w���a�Ϊ���");
				}
				else if(args[0].equals("wallet") || args[0].equals("w") || args[0].equals("my")){
					sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.YELLOW + "�z���W�٦�" + CMoney.getMoneyMap().get(sender.getName()) + "��");
				}else
					return false;
				
				break;
			case "cp$": 
				if(args.length < 1) return false;
				
				if(args[0].equals("help") || args[0].equals("h")) {
					
				}
				else if(args[0].equals("give")) {
					if(args.length >= 3) {
						if(!CPoint.isWL(sender.getName())) {
							sender.sendMessage(CPoint.infoTitle() + TextFormat.RED + "�z�S���������v���i�H���榹���O!");
							return true;
						}
						if(!isDigit(args[2])) {
							sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ʀr�榡���~! ���ˬd�᭫�s��J!");
							return true;
						}
						try {
							float point = (float) Double.parseDouble(args[2]);
							if(sender.isPlayer()) {
								CPoint.givePoint(sender.getName(), args[1], point);
							}else {
								CPoint.givePoint(args[1], point);
							}
							sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.GREEN + 
									"���\����" + args[1] + "���a" + args[2] + "��" + CPoint.config.getString("name", "�I��"));
						}catch(FileNotFoundException err) {
							sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�䤣��Ӫ��a");
						}
					}else 
						sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ы��w���a�Ϊ���");
				}
				else if(args[0].equals("wallet") || args[0].equals("w") || args[0].equals("my")){
					sender.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.YELLOW + "�z���W�٦�" + 
							CMoney.getMoneyMap().get(sender.getName()) + "��" + CPoint.config.getString("name", "�I��"));
				}else
					return false;
				
				break;
			case "cbank":
				if(args.length < 1) {
					if(sender.isPlayer()) {
						sender.sendMessage(PluginInfo.bankStatus((Player) sender));
					}else {
						sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
					}
					return true;
				}
				if(args[0].equals("help") || args[0].equals("h")) {
					sender.sendMessage(PluginInfo.bankHelp());
				}
				else if(args[0].equals("store")) {
					//args[1] dollar
					if(sender.isPlayer()) {
						if(args.length < 2) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Ы��w�����ƶq");
							return true;
						}
						if(!isDigit(args[1])) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�п�J���T�������Ʀr�榡!");
							return true;
						}
						Player p = (Player) sender;
						double money = Double.parseDouble(args[1]);
						if(CBank.getDepositMap().containsKey(p.getName())) {
							CBank.addDeposit(p.getName(), (float) money);
						}else {
							CBank.putDeposit(p.getName(), (float) money);
						}
						CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) - money));
						p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�x�s" + money + "�������ܻȦ�!");
					}else
						sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
				}
				else if(args[0].equals("draw")) {
					//args[1] dollar
					if(sender.isPlayer()) {
						if(args.length < 2) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Ы��w�����ƶq");
							return true;
						}
						if(!isDigit(args[1])) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�п�J���T�������Ʀr�榡!");
							return true;
						}
						Player p = (Player) sender;
						if(!CBank.getDepositMap().containsKey(p.getName())) {
							p.sendMessage(CBank.infoTitle() + TextFormat.GRAY + "�z�S���b�Ȧ��x�s���󪺲{��!");
							return true;
						}else {
							if(CBank.getDepositMap().get(p.getName()) <= 0) {
								p.sendMessage(CBank.infoTitle() + TextFormat.GRAY + "�z�S���b�Ȧ��x�s���󪺲{��!");
								return true;
							}
						}
						double money = Math.min(Double.parseDouble(args[1]), CBank.getDepositMap().get(p.getName()));
						
						CBank.deDeposit(p.getName(), (float) money);
						CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) + money));
						if(CBank.getDepositMap().get(p.getName()) <= 0) {
							CBank.getDepositMap().remove(p.getName());
						}
						p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�q�z���Ȧ�b�ᤤ����" + money + "������!");
					}else
						sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
				}
				else if(args[0].equals("loan")) {
					//args[1] dollar
					if(sender.isPlayer()) {
						if(args.length < 2) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Ы��w�����ƶq");
							return true;
						}
						if(!isDigit(args[1])) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�п�J���T�������Ʀr�榡!");
							return true;
						}
						
						Player p = (Player) sender;
						double loan = Double.parseDouble(args[1]);
						
						if(loan <= CMoney.getMoneyMap().get(p.getName()) * 0.9) {
							if(CBank.getLoanMap().containsKey(p.getName())) {
								if(CBank.getLoanMap().get(p.getName()).getMoney() > 0) {
									p.sendMessage(CBank.infoTitle() + TextFormat.RED + "�z�o�����U�ک|��ú�M�A�Х�ú�M��A�ӽФU�@���U��!");
									return true;
								}
							}
							CBank.putLoan(p.getName(), (float) loan);
							CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) + loan));
							p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�V�Ȧ�U��" +loan + "������!");
						}else {
							p.sendMessage(CBank.infoTitle() + TextFormat.RED + "��p! �z���o�ӽжW�L�z���W����90%���U��\n" + 
									"�z�ܦh�u��ӽ�" + CMoney.getMoneyMap().get(p.getName()) * 0.90 + "�����U��");
						}
					}else
						sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
				}
				else if(args[0].equals("pb")) {
					//args[1] dollar
					if(sender.isPlayer()) {
						if(args.length < 2) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Ы��w�����ƶq");
							return true;
						}
						if(!isDigit(args[1])) {
							sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�п�J���T�������Ʀr�榡!");
							return true;
						}
						
						Player p = (Player) sender;
						if(!CBank.getLoanMap().containsKey(p.getName())) {
							p.sendMessage(CBank.infoTitle() + TextFormat.GRAY + "����! �z�S������U�ڡA�L�����榹���O");
							return true;
						}
						double loan = Math.min(Double.parseDouble(args[1]), CBank.getLoanMap().get(p.getName()).getMoney());
						
						CBank.deLoan(p.getName(), (float) loan);
						CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) - loan));
						
						if(CBank.getLoanMap().get(p.getName()).getMoney() <= 0) {
							p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���߱z�wú�M�o�����Ҧ��U��!");
							CBank.getLoanMap().remove(p.getName());
						}else {
							p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�V�Ȧ�ú��" +loan + "�����U��!");
						}
					}else
						sender.sendMessage(CBank.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
				}
				else if(args[0].equals("stat")) {
					sender.sendMessage(CBank.infoTitle() + TextFormat.YELLOW + "�Ȧ��e�Q�v��: " + CBank.getInterestRate() * 100 + "%");
					sender.sendMessage(CBank.infoTitle() + TextFormat.YELLOW + "�W����s�ɶ���: " + CBank.dateForm());
				}else 
					return false;
				break;
		
			case "cjob":
				if(args.length < 1) return false;
				
				if(args[0].equals("help")) {
					sender.sendMessage(PluginInfo.jobHelp());
				}
				else if(args[0].equals("list")) {
					sender.sendMessage(Job.formList());
				}
				else if(args[0].equals("my")) {
					if(sender.isPlayer()) {
						Player p = (Player) sender;
						CJob myjob = player_Job.get(p.getName());
						p.sendMessage(myjob.information());
					}else {
						sender.sendMessage(CJob.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
					}
				}
				else if(args[0].equals("get")) {
					if(sender.isPlayer()) {
						//args[1] Job
						if(args.length < 2) {
							sender.sendMessage(CJob.infoTitle() + TextFormat.RED + "�п�J���T�����O�榡");
							return false;
						}
						Player p = (Player) sender;
						
						if(Job.getJob(args[1]).equals(Job.NONE)) {
							p.sendMessage(CJob.infoTitle() + TextFormat.RED + "�L�k���o�Ӥu�@�A�п�J /cjob list�d�ݷ�e�u�@�C��");
						}else {
							if(!player_Job.get(p.getName()).getJob().equals(Job.NONE)) {
								p.sendMessage(CJob.infoTitle() + TextFormat.GRAY + "�w���}�F�z������u�@: " + player_Job.get(p.getName()).getJob().getName());
							}
							player_Job.put(p.getName(), new CJob(Job.getJob(args[1]), new JobRecorder()));
							p.sendMessage(CJob.infoTitle() + TextFormat.GREEN + "�w���\�ӽФu�@: " + TextFormat.AQUA + Job.getJob(args[1]).getName());
						}
					}
				}
				else if(args[0].equals("quit")) {
					if(sender.isPlayer()) {
						Player p = (Player) sender;
						if(player_Job.get(p.getName()).getJob().equals(Job.NONE)) {
							p.sendMessage(CJob.infoTitle() + TextFormat.GRAY + "�z��e�L����u�@");
						}else {
							p.sendMessage(CJob.infoTitle() + TextFormat.GRAY + "�z�w�㱼�z��e���u�@: " + player_Job.get(p.getName()).getJob().getName());
							player_Job.get(p.getName()).setJob(Job.NONE);
						}
					}else
						sender.sendMessage(CJob.infoTitle() + TextFormat.RED + "�Цb�C�������榹���O");
				}else
					return false;
				
				break;
		}
		return true;
	}
	
	public static float getD2(double value) {
		return (float)Math.round(value*100)/100;
	}
	
	private static boolean isDigit(String s) {
		Pattern pattern = Pattern.compile("^(\\d*)(\\.\\d*)?$");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}
	
	public void saveAll() {
		
	}
}
