package cosr.economy;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import cosr.economy.bank.CBank;
import cosr.economy.bank.Loan;

public class PluginInfo {
	
	public enum Role {
		CONSOLE,
		OP,
		PLAYER;
	}
	
	public enum Economy {
		MONEY("Money"),
		SHOP("Shop"),
		BANK("Bank"),
		JOB("Job");
		
		private String name;
		
		private Economy(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public static final String COINNAME = "����";
	
	public static final String pluginInfoTitle() {
		return TextFormat.RESET + (TextFormat.BOLD + (TextFormat.WHITE + "[") + (TextFormat.AQUA + "CEconomy") + (TextFormat.WHITE + "]")) + TextFormat.RESET;
	}
	
	public static final String moneyInfoTitle() {
		return TextFormat.RESET + (TextFormat.BOLD + (TextFormat.WHITE + "[") + (TextFormat.AQUA + "CMoney") + (TextFormat.WHITE + "]")) + TextFormat.RESET;
	}
	
	public static final String pluginInfo(Economy type) {
		String title = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.AQUA + "--- CEconomy �g�ٮ֤� v1.0 - " + type.getName() + " ---")) + "\n";
		String separator = TextFormat.RESET + "==========================================================\n";
		
		String authorLine = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.YELLOW + "@author: ")) 
				+ (TextFormat.RESET + (TextFormat.WHITE + "Cmen")) + "\n";
		String teamLine = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.YELLOW + "@team: ")) 
				+ (TextFormat.RESET + (TextFormat.WHITE + "COSR")) + "\n";
		String aboutUsLine = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.YELLOW + "@about us: "))
				+ (TextFormat.RESET + (TextFormat.WHITE + "http://cosr.ddns.net")) + "\n";
		String contactLine = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.YELLOW + "@contact: "))
				+ (TextFormat.RESET + (TextFormat.WHITE + "storm319117@gmail.com")) + "\n";
		
		return separator + title + separator + authorLine + "\n" + teamLine + "\n" + aboutUsLine + "\n" + contactLine + separator;
	}
	
	public static final String moneyHelp() {
		
		String firstPart = TextFormat.RESET + 
				"�@ �������: \n" + 
				"1. �֦������������A�z�i�H�b�C�����H�����ʶR���󪫫~\n" + 
				"2. �z�i�H�H�ɦb������X������L���a����\n" + 
				"3. �z�i�H�z�L�S�w�覡�������\n" + 
				"4. �D���O�� /c$\n";
		
		String secondPart = TextFormat.RESET + 
				"�G �p����o����: \n" + 
				"1. �z�i�H�b�C�������u�@�H��o���� (�Ա��п�J/cjob help�d��)\n" + 
				"2. �z�i�H�Q�έӤH�ө��N���W�h�l���F���X�����ݭn�����a�H��o���� (�Ա��п�J/cshop help�d��)\n" + 
				"3. �z�i�H�z�L���A���ө��N���W�h�l���F���X�H��o���� (�Ա��п�J/cshop help�d��)\n" + 
				"4. �z�i�H�N�����s�J�Ȧ�H�Ȩ���h�Q�� (�Ա��п�J/cbank help�d��)\n"; 
		
		String thirdPart = TextFormat.RESET + 
				"�T �������O: \n" + 
				"1. /c$ help  �d�ݪ������O���U\n" + 
				"2. /c$ give [player] [dollar]  ������L���a����\n" + 
				"3. /c$ wallet  �d�ݦۤv�����]\n";
		
		String morePart = TextFormat.RESET + 
				"�| ��h: \n" + 
				"1. /c$ help �d�ݪ����������U\n" + 
				"2. /cshop help �d�ݰө��������U\n" + 
				"3. /cbank help �d�ݻȦ�������U\n" + 
				"4. /cjob help �d�ݤu�@�������U\n" + 
				"5. �Y��������D���pôCOSR�ζ��H�Χ@��Cmen\n";
		
		return pluginInfo(Economy.MONEY) + firstPart + "\n" + secondPart + "\n" + thirdPart + "\n" + morePart;
	}
	
	public static final String shopHelp() {
		String warning = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.RED + "�ѩ�ө��\���������, �аȥ��J�Ӭݧ�!!\n"));
		
		String firstPart = TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.GREEN + "�@ ����ө�: \n")) + TextFormat.RESET + 
				"1. �ө��������������" + (TextFormat.YELLOW + "SELL(��)") + TextFormat.RESET + "�H��" + (TextFormat.YELLOW + "BUY(�R)\n") + TextFormat.RESET + 
				"2. �z�i�H�I���w�]�m���ө��i�ܵP, �ӧ������~���R��\n" + 
				"3. �z�i�H�ϥΫ��O�غc�ݩ�z���i�ܵP�ө�, �Ա��Ш�" + TextFormat.YELLOW + "(�G �T)\n" + TextFormat.RESET + 
				"4. �ө��i�ܵP�W����r" +TextFormat.YELLOW + "�Ĥ@�C���ө��W��, �ĤG�C���֦��̸�T, �ĤT�C�������T, �ĥ|�C���������\n" + TextFormat.RESET + 
				"5. ��z���Q���z���ө��~����, �i�ϥΫ��O�N���w�s�����ө�����\n" + 
				"6. �Y�n�d�ݰө��s��, �п�J " + (TextFormat.YELLOW + "/cshop list") + TextFormat.RESET + "�K�i�d�ݱz�Ҧ��ө����s���H�θԲӸ�T\n" + TextFormat.RESET + 
				"7. �I���z���ө��N�|���X�ө��H��\n" + 
				"8. �D���O�� /cshop\n";
		
		String secondPart = TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.GREEN + "�G �����X�������ө�: \n")) + TextFormat.RESET + 
				"1. �i�ܵP�W�ĤT�C��r���Y�Ÿ���" + TextFormat.YELLOW + "S\n" + TextFormat.RESET + 
				"2. ��z�I���������ө�, �B��������������, " + (TextFormat.YELLOW + "�z�|����֦��̪��ҳc�檺���~, �P�ɱz�������]�N�i����\n") + TextFormat.RESET + 
				"3. �z�i�H�z�L" + (TextFormat.YELLOW + "���O") +TextFormat.RESET + "�غc���������ө�, �H��X�z���ݭn���F��\n" + TextFormat.RESET + 
				"4. �غc���O��: " + (TextFormat.YELLOW + "/cshop create [�ө��W��] s [���~ID] [����ƶq] [����]\n") + TextFormat.RESET + 
				"5. ���O���槹���I���z�ҷQ�]�m���i�ܵP�H�����غc, ���`�N, �]�m�e���i�ܵP�W���঳��r����\n" + TextFormat.RESET + 
				"6. �غc������, " + (TextFormat.YELLOW + "�z���W���Ӫ��~�N�|���Ʃ�J�z���ө�") + (TextFormat.RESET + ", �H�@��") + (TextFormat.YELLOW + "�w�s\n") + TextFormat.RESET + 
				"7. �z�i�H�ϥΫ��O�����������ө��x�s�����~, �Ϊk: " + (TextFormat.YELLOW + "/cshop extract [�ө�ID] [�ƶq]\n") + TextFormat.RESET + 
				"8. �z�i�H�ϥΫ��O���z���������ө��ɳf, �Ϊk: " + (TextFormat.YELLOW + "/cshop replenish [�ө�ID] [�ƶq]\n") + TextFormat.RESET + 
				"9. �z��i" + (TextFormat.YELLOW + "����z���ө��ҳc�檺���~, �I���z���i�ܵP, �C�I���@���N�|��J�@�Ӫ��~\n") + TextFormat.RESET + 
				"10. �z�i�H�b�z���ө��C���ݨ즹�������ө�, �Ϊk�� " + TextFormat.YELLOW + "/cshop list [����]\n"; 
		
		String thirdPart = TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.GREEN + "�T ����R�i�������ө�: \n")) + TextFormat.RESET + 
				"1. �i�ܵP�W�ĤT�C��r���Y�Ÿ���" + TextFormat.YELLOW + "B\n" + TextFormat.RESET + 
				"2. ��z�I���������ө�, �B�����������~�ƶq��, " + (TextFormat.YELLOW + "�z�i��|���h�@�w�ƶq���Ӫ��~, �P�ɱz�i��|��o�@�w���������S\n") + TextFormat.RESET + 
				"3. �z�i�H�z�L" + (TextFormat.YELLOW + "���O") +TextFormat.RESET + "�غc���������ө�, �H�R�i�z�һݪ����~\n" + TextFormat.RESET + 
				"4. �غc���O��: " + (TextFormat.YELLOW + "/cshop create [�ө��W��] b [���~ID] [����ƶq] [����])\n") + TextFormat.RESET + 
				"5. ���O���槹���I���z�ҷQ�]�m���i�ܵP�H�����غc, ���`�N, �]�m�e���i�ܵP�W���঳��r����\n" + TextFormat.RESET + 
				"6. �z�i�H�ϥΫ��O�����������ө��x�s�����~, �Ϊk: " + (TextFormat.YELLOW + "/cshop extract [�ө�ID] [�ƶq]\n") + TextFormat.RESET + 
				"7. �z�L�k�N���W�����~��J���������ө�\n" + TextFormat.RESET + 
				"8. �z�i�H�b�z���ө��C���ݨ즹�������ө�, �Ϊk�� " + TextFormat.YELLOW + "/cshop list [����]\n";
		
		String forthPart = TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.GREEN + "�| ������A���ө��P�ӤH�ө�: \n")) + TextFormat.RESET + 
				"1. �غc�v��: ���A���ө�" + (TextFormat.DARK_AQUA + "[OP]") + (TextFormat.RESET + " / �ӤH�ө�") + (TextFormat.DARK_AQUA + "[PLAYER]\n") + TextFormat.RESET + 
				"2. �d���v��: ���A���ө�" + (TextFormat.DARK_AQUA + "[OP]") + (TextFormat.RESET + " / �ӤH�ө�") + (TextFormat.DARK_AQUA + "[�֦���]\n") + TextFormat.RESET + 
				"3. �R���v��: ���A���ө�" + (TextFormat.DARK_AQUA + "[OP]") + (TextFormat.RESET + " / �ӤH�ө�") + (TextFormat.DARK_AQUA + "[�֦���]\n") + TextFormat.RESET + 
				"4. �ө��Φ�: ���A���ө�" + (TextFormat.DARK_AQUA + "[�i�ܵP]") + (TextFormat.RESET + " / �ӤH�ө�") + (TextFormat.DARK_AQUA + "[�i�ܵP]\n") + TextFormat.RESET + 
				"5. �������: ���A���ө�" + (TextFormat.DARK_AQUA + "[�R or ��]") + (TextFormat.RESET + " / �ӤH�ө�") + (TextFormat.DARK_AQUA + "[�R or ��]\n") + TextFormat.RESET + 
				"6. �w�s�q: ���A���ө�" + (TextFormat.DARK_AQUA + "[�L��]") + (TextFormat.RESET + " / �ӤH�ө�") + (TextFormat.DARK_AQUA + "[����]\n");
				
		
		String fifthPart = TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.GREEN + "��: ������O: \n")) + TextFormat.RESET + 
				"1. " + TextFormat.DARK_GREEN + "/cshop help  " + TextFormat.RESET + ">�d�ݪ������O���U\n" + 
				"2. " + TextFormat.DARK_GREEN + "/cshop list  " + TextFormat.RESET + ">�d�ݱz�Ҧ��ө����C��\n" + 
				"3. " + TextFormat.DARK_GREEN + "/cshop create [�ө��W��] [�������b/s] [���~ID] [����ƶq] [����]  " + TextFormat.RESET + ">�ЫحӤH�ө�\n" + 
				"4. " + TextFormat.DARK_GREEN + "/cshop delete [�ө�ID]  " + TextFormat.RESET + ">�R�����w�s�����ӤH�ө�\n" + 
				"5. " + TextFormat.DARK_GREEN + "/cshop check [�ө�ID]  " + TextFormat.RESET + ">�d�ݸӽs���ө����ԲӸ�T\n" + 
				"6. " + TextFormat.DARK_GREEN + "/cshop replenish [�ө�ID] [���~�ƶq]  " + TextFormat.RESET + ">�����w�s�����ө��ɳf\n" + 
				"7. " + TextFormat.DARK_GREEN + "/cshop extract [�ө�ID] [���~�ƶq]  " + TextFormat.RESET + ">�q���w�s�����ө������X�f��\n" + 
				"8. " + TextFormat.DARK_GREEN + "/cshop ad [�ө�ID] [�s�i���e] [�s������]  " + TextFormat.RESET + ">�����w�s�����ө����s�i\n" + 
				"9. " + TextFormat.DARK_GREEN + "/cshop ui  " + TextFormat.RESET + ">���}�ө��޲z����\n";
		
		String morePart = TextFormat.RESET + 
				(TextFormat.BOLD + (TextFormat.GREEN + "�� ��h: \n")) + TextFormat.RESET + 
				"1. /c$ help �d�ݪ����������U\n" + 
				"2. /cshop help �d�ݰө��������U\n" + 
				"3. /cbank help �d�ݻȦ�������U\n" + 
				"4. /cjob help �d�ݤu�@�������U\n" + 
				"5. �Y��������D���pôCOSR�ζ��H�Χ@��Cmen\n";
		
		
		return pluginInfo(Economy.SHOP) + warning + "\n" + firstPart + "\n" + secondPart + "\n" + thirdPart + "\n" + forthPart + "\n" + fifthPart + "\n" + morePart;
	}
	
	public static final String bankHelp() {
		
		String firstPart = TextFormat.RESET + 
				"�@ ����Ȧ�: \n" + 
				"1. �z�i�H�N���W�֦��������s�J�Ȧ�, �H�Ȩ���h���Q��\n" + 
				"2. �z�i�H�V�Ȧ�ӽжU��, �H�����z���ݨD(�̰��i�U�z�{�����B��90%)\n" + 
				"3. �U�ڽЩ�7�餺ú�M, �_�h�N���������z���W������\n" + 
				"4. �C�ѻȦ檺�Q�v�N�H���ܧ�\n" + 
				"5. �C�L�@��, �Ȧ�|�H��e�Q�v���s�p��z�w�x�s������\n" + 
				"6. �D���O�� /cbank\n";
		
		String secondPart = TextFormat.RESET + 
				"�G �������O: \n" + 
				"1. /cbank  �d�ݷ�e�Ȧ檬�A�θ�T\n" + 
				"2. /cbank help  �d�ݻȦ���O���U\n" + 
				"3. /cbank store [dollar]  �N���w�ƶq�������s�J�Ȧ�\n" + 
				"4. /cbank draw [dollar]  �N���w�ƶq�������q�Ȧ椤���X\n" + 
				"5. /cbank loan [dollar]  �V�Ȧ�U�ګ��w�ƶq������\n" + 
				"6. /cbank pb [dollar]  �N���w�ƶq���U��ú�ٵ��Ȧ�\n";
		
		String morePart = TextFormat.RESET + 
				"�T ��h: \n" + 
				"1. /c$ help �d�ݪ����������U\n" + 
				"2. /cshop help �d�ݰө��������U\n" + 
				"3. /cbank help �d�ݻȦ�������U\n" + 
				"4. /cjob help �d�ݤu�@�������U\n" + 
				"5. �Y��������D���pôCOSR�ζ��H�Χ@��Cmen\n";
				
		return pluginInfo(Economy.BANK) + firstPart + "\n" + secondPart + "\n" + morePart;
	}
	
	public static final String jobHelp() {
		
		String firstPart = TextFormat.RESET + 
				"�@ ����u�@: \n" + 
				"1. �z�i�H�ϥ� /cjob list�Ӭd�ݷ�e���A���̩Ҧ����u�@\n" + 
				"2. �z�i�H�ӽФu�@�H��o����\n" + 
				"3. �@�Ӫ��a�u���ܤ@���u�@\n" + 
				"4. ��z���W���������t�Ȯ�, �N�L�k���Q�ӽФu�@\n" + 
				"5. �Y�z���\���F���u�@���ݨD, �N�|���H�Ӥu�@�����S\n" + 
				"6. ��z���Q�~�򰵥ثe���u�@, �i�H�����¾\n" + 
				"7. �Y�z��e���u�@, �S�ӽШ�L�u�@, �s�u�@�N�|�л\�¤u�@\n" + 
				"8. �D���O�� /cjob\n";
		
		String secondPart = TextFormat.RESET + 
				"�G �������O: \n" + 
				"1. /cjob help  >�d�ݤu�@���O���U\n" + 
				"2. /cjob list  >�d�ݷ�e���A�����Ҧ����u�@\n" + 
				"3. /cjob get [�u�@�W��]  >�ӽиӤu�@\\n" + 
				"4. /cjob quit  >�㱼��e�u�@\n";
		
		String morePart = TextFormat.RESET + 
				"�T ��h: \n" + 
				"1. /c$ help �d�ݪ����������U\n" + 
				"2. /cshop help �d�ݰө��������U\n" + 
				"3. /cbank help �d�ݻȦ�������U\n" + 
				"4. /cjob help �d�ݤu�@�������U\n" + 
				"5. �Y��������D���pôCOSR�ζ��H�Χ@��Cmen\n";
				
		return pluginInfo(Economy.BANK) + firstPart + "\n" + secondPart + "\n" + morePart;
	}
	
	/*
	public static final String serverShopListInfo(CommandSender sender, int page) {
		
		int maxIndex = CEconomy.getServerShopList().size();
		int maxPage = maxIndex/4 + 1;
		int realPage = (page > maxPage)? maxPage : page;
		if(realPage <= 0) realPage = 1;
		
		String title = TextFormat.RESET + (TextFormat.GREEN + "���A���ө��C��(page " + realPage + "/" + maxPage + ")\n");
		String separator = TextFormat.RESET + "=========================\n";
		
		String shopInfo = "";
		for(int i = (realPage-1)*4; i < realPage*4; i++) {
			if(i < maxIndex) {
				ServerShop svShop = CEconomy.getServerShopList().get(i);
				String num = TextFormat.RESET + (TextFormat.BOLD + "#"+i+": \n");
				
				shopInfo += num + svShop.Information(sender.getName()) + separator;
			}else {
				break;
			}
		}
		
		return title + separator + shopInfo;
		/*
		 * --- ���A���ө��C��(page 1/?) ---
		 * ================================
		 * #1:
		 * �ө��W��: COSR_Shop
		 * �������: ��(SELL)
		 * ���~: 
		 * ���~ID: 
		 * ���: 
		 * ================================
		 *
	}
	*/
	
	/*
	public static final String personalShopListInfo(Player sender, String owner, int page) {
		
		if(!CEconomy.getShopMap().containsKey(owner)) return "No Shop!";
		
		int maxIndex = CEconomy.getShopMap().get(owner).size();
		int maxPage = maxIndex/4 + 1;
		int realPage = (page > maxPage)? maxPage : page;
		
		String title = TextFormat.RESET + (TextFormat.GREEN + "�z�Ҧ����ө��C��(page " + realPage + "/" + maxPage + ")\n");
		String separator = TextFormat.RESET + "=========================\n";
		
		String shopInfo = "";
		for(int i = (realPage-1)*4; i < realPage*4; i++) {
			if(i < maxIndex) {
				PersonalShop pShop = CEconomy.getShopMap().get(owner).get(i);
				String num = TextFormat.RESET + (TextFormat.BOLD + "#"+i+": \n");
				
				shopInfo += num + pShop.Information(sender.getName()) + separator;
			}else {
				break;
			}
		}
		
		return title + separator + shopInfo;
		/*
		 * --- �z�Ҧ����ө��C��(page 1/?) ---
		 * ==================================
		 * #1:
		 * �ө��W��: MyShop
		 * �������: �R(BUY)
		 * ���~: 
		 * ���~ID: 
		 * �@������ƶq: 
		 * �w�s�q: 
		 * ���:
		 * ===================================
		 *
	}
	*/
	
	public static final String bankStatus(Player player) {
		String title = TextFormat.RESET + (TextFormat.GREEN + "--- COSR�Ȧ��e���A --- \n");
		String separator = TextFormat.RESET + "=======================\n";
		String depositLine = TextFormat.RESET + (TextFormat.DARK_GREEN + "�z�ثe�x�s�����B: " + 
				TextFormat.RESET + CEconomy.getD2(CBank.getDepositMap().getOrDefault(player.getName(), (float)0.0)) + "\n");
		String loanLine = TextFormat.RESET + (TextFormat.DARK_GREEN + "�z�ثe�U�ڪ����B: " + 
				TextFormat.RESET + CEconomy.getD2(CBank.getLoanMap().getOrDefault(player.getName(), new Loan()).getMoney()) + "\n");
		String loanDaysLine = TextFormat.RESET + (TextFormat.DARK_GREEN + "�U�ڤw�L�Ѽ�: " + 
				TextFormat.RESET + CBank.getLoanMap().getOrDefault(player.getName(), new Loan()).getDays() + "\n");
		String IRLine = TextFormat.RESET + (TextFormat.DARK_GREEN + "��e�Ȧ�Q�v: " + 
				TextFormat.RESET + CEconomy.getD2(CBank.getInterestRate())*100 + "%" + "\n");
		String lastDate = TextFormat.RESET + (TextFormat.DARK_GREEN + "�W����s�ɶ�: " + 
				TextFormat.RESET + CBank.dateForm() + "\n");
		
		return title + separator + depositLine + loanLine + loanDaysLine + IRLine + lastDate;
		
		/*
		 * --- COSR�Ȧ��e���A --- 
		 * =======================
		 * �z�ثe�x�s�����B: 
		 * �z�ثe�U�ڪ����B: 
		 * �U�ڤw�L�Ѽ�: 
		 * �Ȧ�Q�v: 
		 * �W���Q�v��s���: 
		 */
	}
}
