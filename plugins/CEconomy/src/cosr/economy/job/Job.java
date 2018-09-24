package cosr.economy.job;

import cn.nukkit.utils.TextFormat;

public enum Job {
	LAMBERJACK("Lamberjack", "�C���U64�ӭ��h��o30��", 64, 30.0),
	MINER("Miner", "�C�����64���Z�Z�۫h��o15��", 64, 15.0),
	GARDENER("Gardener", "�C����U64�Ӿ𸭤���h��o20��", 64, 20.0),
	DIGGER("Digger", "�C����U64�Ӫd�g���h��o10��", 64, 10.0),
	NONE("None", "�ۥѵL�~�̡A�S�Ϊ̬O�ǥ���(�L�k�ǥѥӽШ��o)", null, 0.0);
	
	private String name;
	private String description;
	private Object requirement;
	private double money;
	
	private Job(String name, String description, Object requirement, double money) {
		this.name = name;
		this.description = description;
		this.requirement = requirement;
		this.money = money;
	}
	
	public String getName() {
		return name;
	}
	
	public String chineseName() {
		switch(this) {
			case LAMBERJACK:
				return "���u";
			case MINER:
				return "�q�u";
			case GARDENER:
				return "��B";
			case DIGGER:
				return "�����u";
			case NONE:
				return "�L�~��";
			default:
				return "�����ǫ�";
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public Object getRequirement() {
		return requirement;
	}
	
	public double getMoney() {
		return money;
	}

	public static Job getJob(String name) {
		if(name.equalsIgnoreCase("Lamberjack")) {
			return Job.LAMBERJACK;
		}
		if(name.equalsIgnoreCase("Miner")) {
			return Job.MINER;
		}
		if(name.equalsIgnoreCase("Gardener")) {
			return Job.GARDENER;
		}
		if(name.equalsIgnoreCase("Digger")) {
			return Job.DIGGER;
		}
		//else
		return Job.NONE;
	}
	
	public static String formList() {
		String title = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.GREEN + "<<===== �u�@�C�� =====>>\n"));
		String jobList = "";
		for(Job job : Job.values()) {
			jobList += TextFormat.RESET + 
					(TextFormat.DARK_GREEN + job.getName()) + "(" + job.chineseName() + "): " + 
					TextFormat.RESET + job.getDescription() + "\n";
		}
		
		return title + jobList;
	}
	
	public void saveConfig() {
		//Only convenient for reading
	}
}