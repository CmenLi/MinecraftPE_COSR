package cosr.newbie;

import java.util.LinkedList;
import java.util.Queue;

import cmen.essalg.CJEF;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import cosr.newbie.event.BuffSetter;
import cosr.newbie.event.MobSpawner;
import cosr.newbie.event.SpawnZone;

public class BuildTool {

	public Queue<SpawnZone> tempZones = new LinkedList<SpawnZone>();
	private MobSpawner tempMS;
	private BuffSetter tempBS;
	private String effectType;
	private Player builder;
	public int step = 0;
	
	public BuildTool(Player p) {
		this.builder = p;
	}
	
	public String getEffectType() {
		return effectType;
	}

	public Player getBuilder() {
		return builder;
	}
	
	public MobSpawner getTempMobSpawner() {
		return tempMS;
	}

	public void setTempMobSpawner(MobSpawner tempMS) {
		this.tempMS = tempMS;
	}

	public BuffSetter getTempBuffSetter() {
		return tempBS;
	}
	public void setTempBuffSetter(BuffSetter setter) {
		tempBS = setter;
	}

	public void showEffectSelector() {
		FormWindowSimple window = new FormWindowSimple("�п�ܮĪG", "");
		window.addButton(new ElementButton(TextFormat.BOLD + "�ͦ��ͪ� MobSpawn"));
		window.addButton(new ElementButton(TextFormat.BOLD + "���A�ĪG EffectSet"));
		builder.showFormWindow(window);
	}
	
	public void onEffectSelect(String effect) {
		effectType = effect;
		if(effectType.equals("EffectSet"))
			tempBS = new BuffSetter(CNewbie.NBVillage, "Player");
		else if(effectType.equals("MobSpawn")) {
			tempMS = new MobSpawner(CNewbie.NBVillage, "Player", true);
		}
		step++;
	}
	
	public void showDetectTypeSetter() {
		FormWindowCustom window = new FormWindowCustom("�]�w������������");
		window.addElement(new ElementInput("�п�J�ͪ�����", "ex: Player", "Player"));
		builder.showFormWindow(window);
	}
	
	public void showMobSpawnSetter() {
		FormWindowCustom window = new FormWindowCustom("�]�w�ͪ������Υͦ��q");
		window.addElement(new ElementInput("�п�J�ͪ�����", "ex: Cow"));
		window.addElement(new ElementInput("�п�J�ͦ��ƶq", "ex: 5", "0"));
		builder.showFormWindow(window);
	}
	
	public void showBuffSetter() {
		FormWindowCustom window = new FormWindowCustom("�]�w���A�ĪG");
		ElementDropdown effList = new ElementDropdown("�п�ܮĪG");
		setDropdownOptions(effList, "�M���Ҧ��ĪG", 
				"�[�t", "�w�t", "�����[�t", "������t", "�j�O", "�ߧY�v��", "�ߧY�ˮ`", "���D����", "����", "�^�_", 
				"�ܩ�", "�ܤ���", "�����I�l", "����", "����", "�]��", "���j", "��z", "���r", "��s", 
				"�ͩR�ȴ���", "�l��", "����", "�o��", "�}�B", "���B", "�`�B", "�w��", "���F����", "���b�����f");
		window.addElement(effList);
		window.addElement(new ElementInput("�п�J����ɶ�(s)", "ex: 10", "0"));
		window.addElement(new ElementInput("�п�J���ŭ��v", "ex: 1", "0"));
		builder.showFormWindow(window);
	}
	
	public void onMobSpawnSetting(FormResponseCustom response) {
		String entityType = response.getInputResponse(0);
		String amountStr = response.getInputResponse(1);
		if(!CJEF.isInteger(amountStr)) {
			builder.sendMessage(TextFormat.RED + "�п�J���T���ƶq");
			showMobSpawnSetter();
			return;
		}
		int amount = Integer.parseInt(amountStr);
		SpawnZone tempZone = tempZones.poll();
		if(tempZone != null) {
			tempZone.setEntityType(entityType);
			tempZone.setSpawnAmount(amount);
			tempMS.putSpawnZone(tempZone);
		}
		builder.sendMessage(CNewbie.TITLE + TextFormat.ITALIC + (TextFormat.YELLOW + "���I���ͦ��϶�"));
	}
	
	public void onBuffSetting(FormResponseCustom response) {
		int effId = response.getDropdownResponse(0).getElementID();
		String durationStr = response.getInputResponse(1);
		if(!CJEF.isInteger(durationStr)) {
			builder.sendMessage(TextFormat.RED + "��Ƴ����u���������, �Э��s��J");
			showBuffSetter();
			return;
		}
		String amplifierStr = response.getInputResponse(2);
		if(!CJEF.isInteger(amplifierStr)) {
			builder.sendMessage(TextFormat.RED + "���ŭ��v�����u���������, �Э��s��J");
			showBuffSetter();
			return;
		}
		int duration = Integer.parseInt(durationStr);
		int amplifier = Integer.parseInt(amplifierStr);
		if(tempBS != null) {
			tempBS.setEffect(effId, duration, amplifier);
			CNewbie.BSPool.add(tempBS);
			builder.sendMessage(CNewbie.TITLE + TextFormat.ITALIC + 
					(TextFormat.GREEN + "�w����" + TextFormat.WHITE + BuffSetter.class.getSimpleName() + TextFormat.GREEN + "���]�w"));
			step = 0;
		}
	}
	
	public void prompt() {
		switch(step) {
		case 0:
			builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�{�b�}�l�Ĥ@�B, �п��Ĳ�o�ƥ�"));
			break;
		case 1:
			builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�ĤG�B, ���I���a�O�H�]�w�����ϰ�(��ѫǿ�J/nb next�i��U�@�B)"));
			break;
		case 2:
			builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�ĤT�B, �п�ܰ�����������"));
			break;
		case 3:
			if(effectType.equalsIgnoreCase("MobSpawn")) {
				builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�ĥ|�B, ���I���ͪ��ͦ��a�I(��ѫǿ�J@done�����]�w)"));
			}
		}
	}
	
	private static ElementDropdown setDropdownOptions(ElementDropdown dpList, String... options) {
		if(dpList != null) {
			for(String option : options) {
				dpList.addOption(option);
			}
		}
		return dpList;
	}
}
