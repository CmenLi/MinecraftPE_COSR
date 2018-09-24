package cosr.economy;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import cosr.economy.bank.CBank;
import cosr.economy.bank.Loan;
import cosr.economy.job.CJob;
import cosr.economy.job.Job;
public class EconomyGUI {
	
	/**���� TODO:Simple*/
	public static FormWindowSimple homePage() {
		FormWindowSimple window = new FormWindowSimple("�g�٭���", "�п�ܥ\��");
		window.addButton(new ElementButton(TextFormat.BOLD + "����@�ɸg��"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�ڪ����]"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�ڪ��u�@"));
		window.addButton(new ElementButton(TextFormat.BOLD + "COSR�Ȧ�"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��������")));
		return window;
	}
	
	/*����g��CEconomy TODO:Simple*/
	public static FormWindowSimple aboutWindow() {
		FormWindowSimple window = new FormWindowSimple("����g��CEconomy", "");
		window.addButton(new ElementButton(TextFormat.BOLD + "���󴡥�"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�������"));
		window.addButton(new ElementButton(TextFormat.BOLD + "����ө�"));
		window.addButton(new ElementButton(TextFormat.BOLD + "����u�@"));
		window.addButton(new ElementButton(TextFormat.BOLD + "����Ȧ�"));
		window.addButton(new ElementButton(TextFormat.BOLD + "����@��"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���")));
		return window;
	}
	
	//.....�o�@��About�u�����ӷQ�g
	//���A�g�g�߱o �N��N��@�U�]�n
	
	/*�ڪ����] TODO:Simple*/
	public static FormWindowSimple moneySystemWindow(String owner) {
		FormWindowSimple window = new FormWindowSimple("�ڪ����]", "�z�٦�"+CMoney.getMoneyMap().get(owner)+"��������");
		window.addButton(new ElementButton(TextFormat.BOLD + "�������a����"));							//giveMoneyWindow
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���")));			//homePage
		return window;
	}
	
	//�������a���� TODO:Custom
	public static FormWindowCustom giveMoneyWindow() {
		FormWindowCustom window = new FormWindowCustom("�������a����");
		window.addElement(new ElementInput("�п�J��H"));
		window.addElement(new ElementInput("�п�J�����ƶq"));
		
		return window;
	}
	
	//�������a...���� TODO:Custom
	public static FormWindowCustom giveMoneyWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("�������a" + playerName + "����");
		window.addElement(new ElementInput("�п�J�����ƶq"));
		
		return window;
	}
	
	//�ڪ��u�@ (DONE) TODO:Simple
	public static FormWindowSimple myJobWindow(String owner) {
		Job job = CEconomy.getJobMap().get(owner).getJob();
		FormWindowSimple window = new FormWindowSimple("�ڪ��u�@", "�z�{�b���u�@��" + TextFormat.DARK_GREEN + job.chineseName());
		window.addButton(new ElementButton(TextFormat.BOLD + "�d�ݷ�e�u�@"+job.getName()));
		window.addButton(new ElementButton(TextFormat.BOLD + "���o��L�u�@"));
		if(!job.getName().equals("None")) {
			window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��¾")));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���")));
		return window;
	}
	
	//�ڪ��u�@��T TODO:Modal
	public static FormWindowModal myJobInfoWindow(String playerName) {
		CJob cjob = CEconomy.getJobMap().get(playerName);
		FormWindowModal window = new FormWindowModal("�ڪ��u�@��T", "", "�T�{", "��^");
		window.setContent(TextFormat.DARK_GREEN + "�u�@�W��: " + TextFormat.RESET + cjob.getJob().chineseName() + "(" + cjob.getJob().getName() + ")\n" + 
							TextFormat.DARK_GREEN + "�u�@����: " + TextFormat.RESET + cjob.getJob().getDescription() + "\n" + 
							TextFormat.DARK_GREEN + "��e�i��: " + TextFormat.RESET + cjob.getRecorder().getCount() + "/" + cjob.getJob().getRequirement());
		return window;
	}
	
	//���o��L�u�@ TODO:Simple
	public static FormWindowSimple allJobWindow() {
		FormWindowSimple window = new FormWindowSimple("���o��L�u�@", "�п�ܱz�ҷQ�n���u�@");
		for(Job j : Job.values()) {
			window.addButton(new ElementButton(j.chineseName() + " " + j.getName()));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	//�T�w���o�Ӥu�@? TODO:Modal
	public static FormWindowModal sureToGetJobWindow(Job job) {
		FormWindowModal window = new FormWindowModal("�T�w���o�Ӥu�@?", "", "�T�w", "����");
		window.setContent(TextFormat.DARK_GREEN + "�u�@�W��: " + TextFormat.RESET + job.chineseName() + "(" + job.getName() + ")\n" + 
				TextFormat.DARK_GREEN + "�u�@����: " + TextFormat.RESET + job.getDescription());
		return window;
	}
	
	//�T�w���}�Ӥu�@? TODO:Modal
	public static FormWindowModal sureToQuitJobWindow(String playerName) {
		CJob cjob = CEconomy.getJobMap().get(playerName);
		FormWindowModal window = new FormWindowModal("�T�w���}�Ӥu�@?", "", "�T�w", "����");
		window.setContent(TextFormat.DARK_GREEN + "�u�@�W��: " + TextFormat.RESET + cjob.getJob().chineseName() + "(" + cjob.getJob().getName() + ")\n" + 
				TextFormat.DARK_GREEN + "�u�@����: " + TextFormat.RESET + cjob.getJob().getDescription() + "\n" + 
				TextFormat.DARK_GREEN + "��e�i��: " + TextFormat.RESET + cjob.getRecorder().getCount() + "/" + cjob.getJob().getRequirement());
		return window;
	}
	
	/*COSR�Ȧ� (DONE) TODO:Simple*/
	public static FormWindowSimple bankSystemWindow() {
		FormWindowSimple window = new FormWindowSimple("COSR�Ȧ�", "");
		window.addButton(new ElementButton(TextFormat.BOLD + "�d�ݻȦ��e���A"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�ڪ��Ȧ�b��"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���")));
		return window;
	}
	
	//COSR�Ȧ��e���A TODO:Modal
	public static FormWindowModal cBankInfoWindow() {
		FormWindowModal window = new FormWindowModal("COSR�Ȧ��e���A", "", "�T�{", "��^");
		window.setContent(TextFormat.DARK_GREEN + "�Ȧ��e�Q�v: " + TextFormat.RESET + (float)CBank.getInterestRate() + "\n" + 
							TextFormat.DARK_GREEN + "�W����s�ɶ�: " + TextFormat.RESET + CBank.dateForm());
		return window;
	}
	
	//�ڪ��Ȧ�b�� TODO:Simple
	public static FormWindowSimple myBalanceWindow(String playerName) {
		FormWindowSimple window = new FormWindowSimple("�ڪ��Ȧ�b��", "");
		window.addButton(new ElementButton(TextFormat.BOLD + "�d�ݸԲӸ��"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�s�J�{��"));
		if(CBank.getDepositMap().containsKey(playerName)) {
			if(CBank.getDepositMap().get(playerName) > 0)
				window.addButton(new ElementButton(TextFormat.BOLD + "�����{��"));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + "�ӽжU��"));
		if(CBank.getLoanMap().containsKey(playerName)) {
			if(CBank.getLoanMap().get(playerName).getMoney() > 0)
				window.addButton(new ElementButton(TextFormat.BOLD + "ú�ٶU��"));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	//�ڪ��b�᪬�A TODO:Modal
	public static FormWindowModal myBalanceInfoWindow(String playerName) {
		FormWindowModal window = new FormWindowModal("�ڪ��b�᪬�A", "", "�T�{", "��^");
		Loan loan = CBank.getLoanMap().get(playerName);
		window.setContent(TextFormat.DARK_GREEN + "��e�s��: " + TextFormat.RESET + CBank.getDepositMap().getOrDefault(playerName, (float)0.0) + "\n" + 
							TextFormat.DARK_GREEN + "��e�U��: " + TextFormat.RESET + (loan == null? "0.0" : loan.getMoney()) + "\n" + 
							TextFormat.DARK_GREEN + "�����U�ڤw�L�Ѽ�: " + TextFormat.RESET + (loan == null? "0" : loan.getDays()) + "\n" + 
							TextFormat.DARK_GREEN + "�Ȧ��e�Q�v: " + TextFormat.RESET + CBank.getInterestRate() + "\n" + 
							TextFormat.DARK_GREEN + "�W����s���: " + TextFormat.RESET + CBank.dateForm());
		return window;
	}
	
	//�N�{���s�J�Ȧ� TODO:Custom
	public static FormWindowCustom storeMoneyWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("�N�{���s�J�Ȧ�");
		String info = TextFormat.DARK_GREEN + "��e�s��: " + TextFormat.RESET + CBank.getDepositMap().getOrDefault(playerName, (float)0.0) + "\n" + 
						TextFormat.DARK_GREEN + "�Ȧ��e�Q�v: " + TextFormat.RESET + CBank.getInterestRate() + "\n" + 
						TextFormat.DARK_GREEN + "�W����s�ɶ�: " + TextFormat.RESET + CBank.dateForm();
		window.addElement(new ElementLabel(info));
		window.addElement(new ElementInput("�п�J�s�J�{���ƶq"));
		
		return window;
	}
	
	//�����{�� TODO:Custom
	public static FormWindowCustom drawMoneyWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("�����{��");
		String info = TextFormat.DARK_GREEN + "��e�s��: " + TextFormat.RESET + CBank.getDepositMap().getOrDefault(playerName, (float)0.0) + "\n" + 
						TextFormat.DARK_GREEN + "�Ȧ��e�Q�v: " + TextFormat.RESET + CBank.getInterestRate() + "\n" + 
						TextFormat.DARK_GREEN + "�W����s�ɶ�: " + TextFormat.RESET + CBank.dateForm();
		window.addElement(new ElementLabel(info));
		window.addElement(new ElementInput("�п�J�����{���ƶq"));
		
		return window;
	}
	
	//�ӽжU�� TODO:Custom
	public static FormWindowCustom loanWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("�ӽжU��");
		String info = TextFormat.ITALIC + (TextFormat.YELLOW + "�`�N: �@���ɶU�̦h�u��ӽбz���W������90%, �ýЩ�C�餺ú�M, �_�h�N�����q�z���W����������");
		window.addElement(new ElementLabel(info));
		window.addElement(new ElementInput("�п�J�ӶU�{���ƶq"));
		
		return window;
	}
	
	//ú�ٶU�� TODO:Custom
	public static FormWindowCustom payLoanWindow(String playerName) {
		FormWindowCustom window = new FormWindowCustom("ú�ٶU��");
		String info = TextFormat.ITALIC + (TextFormat.YELLOW + "�`�N: �Щ�C�餺ú�M�o���U��, �_�h�N�����q�z���W����������");
		window.addElement(new ElementLabel(info));
		window.addElement(new ElementInput("�п�Jú�ٲ{���ƶq"));
		
		return window;
	}
}
