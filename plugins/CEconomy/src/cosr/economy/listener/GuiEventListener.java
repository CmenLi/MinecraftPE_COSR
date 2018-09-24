package cosr.economy.listener;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.*;
import cn.nukkit.form.window.*;
import cn.nukkit.utils.TextFormat;
import cosr.economy.CEconomy;
import cosr.economy.CMoney;
import cosr.economy.EconomyGUI;
import cosr.economy.PluginInfo;
import cosr.economy.bank.CBank;
import cosr.economy.job.CJob;
import cosr.economy.job.Job;
import cosr.economy.job.JobRecorder;

public class GuiEventListener implements Listener {
	public static HashMap<String, Job> uiJobMap = new HashMap<String, Job>();
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onForm(PlayerFormRespondedEvent event) {
		Player p = event.getPlayer();
		FormWindow w = event.getWindow();
		FormResponse r = event.getResponse();
		
		if(r == null) {
			if(uiJobMap.containsKey(p.getName())) uiJobMap.remove(p.getName());
			return;
		}
		
		if(w instanceof FormWindowSimple) {
			//�����B����g��CEconomy�B�ڪ����]�B�����BCOSR�ө������B���a�ө������B���U�ө�(shop.getName())�B�ڪ��ө��C��B�ڪ��ө��t��(title)�B�ڪ��u�@�B���o��L�u�@�BCOSR�Ȧ�B�ڪ��Ȧ�b��
			FormWindowSimple window = (FormWindowSimple) w;
			FormResponseSimple response = (FormResponseSimple) r;
			String btxt = response.getClickedButton().getText();
			if(window.getTitle().equals("�g�٭���")) {
				if(btxt.equals(TextFormat.BOLD + "����@�ɸg��")) {
					p.showFormWindow(EconomyGUI.aboutWindow());
				}
				else if(btxt.equals(TextFormat.BOLD + "�ڪ����]")) {
					p.showFormWindow(EconomyGUI.moneySystemWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�ڪ��u�@")) {
					p.showFormWindow(EconomyGUI.myJobWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "COSR�Ȧ�")) {
					p.showFormWindow(EconomyGUI.bankSystemWindow());
				}
			}
			else if(window.getTitle().equals("����g��CEconomy")) {
				if(btxt.equals("")) {
					
				}
				else if(btxt.equals("")) {
					
				}
				else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��������"))) {
				}
			}
			else if(window.getTitle().equals("�ڪ����]")) {
				if(btxt.equals(TextFormat.BOLD + "�������a����")) {
					p.showFormWindow(EconomyGUI.giveMoneyWindow());
				}
				else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���"))) {
					p.showFormWindow(EconomyGUI.homePage());
				}
			}
			else if(window.getTitle().equals("�ڪ��u�@")) {
				if(btxt.startsWith(TextFormat.BOLD + "�d�ݷ�e�u�@")) {
					p.showFormWindow(EconomyGUI.myJobInfoWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "���o��L�u�@")) {
					p.showFormWindow(EconomyGUI.allJobWindow());
				}
				else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��¾"))) {
					p.showFormWindow(EconomyGUI.sureToQuitJobWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���"))) {
					p.showFormWindow(EconomyGUI.homePage());
				}
			}
			else if(window.getTitle().equals("���o��L�u�@")) {
				if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					p.showFormWindow(EconomyGUI.myJobWindow(p.getName()));
				}else {
					Job job = Job.getJob(btxt.split(" ")[1]);
					p.showFormWindow(EconomyGUI.sureToGetJobWindow(job));
					uiJobMap.put(p.getName(), job);
				}
			}
			else if(window.getTitle().equals("COSR�Ȧ�")) {
				if(btxt.startsWith(TextFormat.BOLD + "�d�ݻȦ��e���A")) {
					p.showFormWindow(EconomyGUI.cBankInfoWindow());
				}
				else if(btxt.equals(TextFormat.BOLD + "�ڪ��Ȧ�b��")) {
					p.showFormWindow(EconomyGUI.myBalanceWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���"))) {
					p.showFormWindow(EconomyGUI.homePage());
				}
			}
			else if(window.getTitle().equals("�ڪ��Ȧ�b��")) {
				if(btxt.startsWith(TextFormat.BOLD + "�d�ݸԲӸ��")) {
					p.showFormWindow(EconomyGUI.myBalanceInfoWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�s�J�{��")) {
					p.showFormWindow(EconomyGUI.storeMoneyWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�����{��")) {
					p.showFormWindow(EconomyGUI.drawMoneyWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�ӽжU��")) {
					p.showFormWindow(EconomyGUI.loanWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "ú�ٶU��")) {
					p.showFormWindow(EconomyGUI.payLoanWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					p.showFormWindow(EconomyGUI.bankSystemWindow());
				}
			}
		}
		else if(w instanceof FormWindowCustom) {
			//�������a�����B�������a...�����B�ЫحӤH�ө��B��...�ө��ɳf�B�q...�ө����������~�B��...���s�i�B�N�{���s�J�Ȧ�B�����{���B�ӽжU�ڡBú�ٶU��
			FormWindowCustom window = (FormWindowCustom) w;
			FormResponseCustom response = (FormResponseCustom) r;
			
			if(window.getTitle().equals("�������a����")) {
				String to = response.getInputResponse(0);
				String moneyStr = response.getInputResponse(1);
				if(!isDigit(moneyStr)) {
					p.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ʀr�榡���~, �Э��s��J�����ƶq");
					p.showFormWindow(EconomyGUI.giveMoneyWindow());
					return;
				}
				double money = Double.parseDouble(moneyStr);
				try {
					CMoney.giveMoney(p.getName(), to, (float)money);
				} catch (FileNotFoundException e) {
					p.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�䤣��Ӫ��a");
					e.printStackTrace();
				}
			}
			else if(window.getTitle().startsWith("�������a") && window.getTitle().endsWith("����")) {
				String playerName = window.getTitle().replace("�������a", "").replace("����", "").trim();
				String moneyStr = response.getInputResponse(0);
				if(!isDigit(moneyStr)) {
					p.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ʀr�榡���~, �Э��s��J�����ƶq");
					p.showFormWindow(EconomyGUI.giveMoneyWindow());
					return;
				}
				double money = Double.parseDouble(moneyStr);
				try {
					CMoney.giveMoney(p.getName(), playerName, (float)money);
					p.sendMessage(TextFormat.GREEN + "���\�������a" + TextFormat.RESET + playerName + TextFormat.YELLOW
							+ (float)money + "������");
				} catch (FileNotFoundException e) {
					p.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�䤣��Ӫ��a");
				}
			}
			else if(window.getTitle().equals("�N�{���s�J�Ȧ�")) {
				String dollarStr = response.getInputResponse(1);
				if(!isDigit(dollarStr)) {
					p.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ʀr�榡���~, �Э��s��J�����ƶq");
					p.showFormWindow(EconomyGUI.storeMoneyWindow(p.getName()));
					return;
				}
				double dollar = Double.parseDouble(dollarStr);
				CBank.addDeposit(p.getName(), (float) dollar);
				CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) - dollar));
				p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "�z���\�s�J�F" + dollar + "�����{��!");
			}
			else if(window.getTitle().equals("�����{��")) {
				String dollarStr = response.getInputResponse(1);
				if(!isDigit(dollarStr)) {
					p.sendMessage(PluginInfo.moneyInfoTitle() + TextFormat.RED + "�Ʀr�榡���~, �Э��s��J�����ƶq");
					p.showFormWindow(EconomyGUI.storeMoneyWindow(p.getName()));
					return;
				}
				double dollar = Math.min(Double.parseDouble(dollarStr), CBank.getDepositMap().get(p.getName()));
				CBank.deDeposit(p.getName(), (float)dollar);
				CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) + dollar));
				if(CBank.getDepositMap().get(p.getName()) <= 0) {
					CBank.getDepositMap().remove(p.getName());
				}
				p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�q�z���Ȧ�b�ᤤ����" + (float)dollar + "������!");
			}
			else if(window.getTitle().equals("�ӽжU��")) {
				String loanStr = response.getInputResponse(1);
				if(!isDigit(loanStr)) {
					p.sendMessage(CBank.infoTitle() + TextFormat.RED + "�п�J���T�������Ʀr�榡!");
					p.showFormWindow(EconomyGUI.loanWindow(p.getName()));
					return;
				}
				double loan = Double.parseDouble(loanStr);
				
				if(loan <= CMoney.getMoneyMap().get(p.getName()) * 0.9) {
					if(CBank.getLoanMap().containsKey(p.getName())) {
						if(CBank.getLoanMap().get(p.getName()).getMoney() > 0) {
							p.sendMessage(CBank.infoTitle() + TextFormat.RED + "�z�o�����U�ک|��ú�M�A�Х�ú�M��A�ӽФU�@���U��!");
							return;
						}
					}
					CBank.putLoan(p.getName(), (float) loan);
					CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) + loan));
					p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�V�Ȧ�U��" +loan + "������!");
				}else {
					p.sendMessage(CBank.infoTitle() + TextFormat.RED + "��p! �z���o�ӽжW�L�z���W����90%���U��\n" + 
							"�z�ܦh�u��ӽ�" + CMoney.getMoneyMap().get(p.getName()) * 0.90 + "�����U��");
					p.showFormWindow(EconomyGUI.loanWindow(p.getName()));
					return;
				}
			}
			else if(window.getTitle().equals("ú�ٶU��")) {
				String loanStr = response.getInputResponse(1);
				if(!isDigit(loanStr)) {
					p.sendMessage(CBank.infoTitle() + TextFormat.RED + "�п�J���T�������Ʀr�榡!");
					p.showFormWindow(EconomyGUI.payLoanWindow(p.getName()));
					return;
				}
				double loan = Math.min(Double.parseDouble(loanStr), CBank.getLoanMap().get(p.getName()).getMoney());
				
				CBank.deLoan(p.getName(), (float) loan);
				CMoney.getMoneyMap().put(p.getName(), (float)(CMoney.getMoneyMap().get(p.getName()) - loan));
				
				if(CBank.getLoanMap().get(p.getName()).getMoney() <= 0) {
					p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���߱z�wú�M�o�����Ҧ��U��!");
					CBank.getLoanMap().remove(p.getName());
				}else {
					p.sendMessage(CBank.infoTitle() + TextFormat.GREEN + "���\�V�Ȧ�ú��" +loan + "�����U��!");
				}
			}
		}
		else if(w instanceof FormWindowModal) {
			//�ө��T���B������\(�ʶR/�X��)�B�ڪ��ө���T�B�T�w�R���Ӱө�?�B�ڪ��u�@��T�B�T�w���o�Ӥu�@?�B�T�w���}�Ӥu�@?�BCOSR�Ȧ��e���A�B�ڪ��b�᪬�A
			FormWindowModal window = (FormWindowModal) w;
			FormResponseModal response = (FormResponseModal) r;
			String btxt = response.getClickedButtonText();
			
			if(window.getTitle().equals("�ڪ��u�@��T")) {
				if(btxt.equals("��^")) {
					p.showFormWindow(EconomyGUI.myJobWindow(p.getName()));
				}
			}
			else if(window.getTitle().equals("�T�w���o�Ӥu�@?")) {
				if(btxt.equals("�T�w")) {
					CEconomy.getJobMap().put(p.getName(), new CJob(uiJobMap.get(p.getName()), new JobRecorder()));
					if(CEconomy.getJobMap().containsKey(p.getName())) {
						if(!CEconomy.getJobMap().get(p.getName()).getJob().equals(Job.NONE)) {
							p.sendMessage(CJob.infoTitle() + TextFormat.GRAY + "�w���}�F�z������u�@: " + CEconomy.getJobMap().get(p.getName()).getJob().getName());
						}
					}
					p.sendMessage(CJob.infoTitle() + TextFormat.GREEN + "�w���\�ӽФu�@: " + TextFormat.AQUA + uiJobMap.get(p.getName()).getName());
				}else if(btxt.equals("����")){
					p.showFormWindow(EconomyGUI.allJobWindow());
				}
				uiJobMap.remove(p.getName());
			}
			else if(window.getTitle().equals("�T�w���}�Ӥu�@?")) {
				if(btxt.equals("�T�w")) {
					if(CEconomy.getJobMap().containsKey(p.getName())) {
						p.sendMessage(CJob.infoTitle() + TextFormat.GRAY + "�z�w�㱼�z��e���u�@: " + CEconomy.getJobMap().get(p.getName()).getJob().getName());
						CEconomy.getJobMap().get(p.getName()).setJob(Job.NONE);
					}
					p.sendMessage(CJob.infoTitle() + TextFormat.GREEN + "�w���\�ӽФu�@: " + TextFormat.AQUA + uiJobMap.get(p.getName()).getName());
				}else if(btxt.equals("����")){
					p.showFormWindow(EconomyGUI.allJobWindow());
				}
				uiJobMap.remove(p.getName());
			}
			else if(window.getTitle().equals("COSR�Ȧ��e���A")) {
				if(btxt.equals("��^")) {
					p.showFormWindow(EconomyGUI.bankSystemWindow());
				}
			}
			else if(window.getTitle().equals("�ڪ��b�᪬�A")) {
				if(btxt.equals("��^")) {
					p.showFormWindow(EconomyGUI.myBalanceWindow(p.getName()));
				}
			}
			else if(window.getTitle().equals("���~")) {
				if(btxt.equals("����")) {
					p.showFormWindow(EconomyGUI.homePage());
				}
			}
		}
	}
	
	private static boolean isDigit(String s) {
		Pattern pattern = Pattern.compile("^(\\d*)(\\.\\d*)?$");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}
}
