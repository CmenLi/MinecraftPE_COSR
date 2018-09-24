package cosr.shop.utils;

import cn.nukkit.utils.TextFormat;
import cosr.economy.CEconomy;
import cosr.shop.shops.CShop;

public class CAdvertisement {
	
	private CShop shop;
	private String content;
	private int times;
	
	public CAdvertisement(CShop shop) {
		this(shop, "Welcome to my shop!", 1);
	}
	
	public CAdvertisement(CShop shop, String content) {
		this(shop, content, 1);
	}
	
	public CAdvertisement(CShop shop, String content, int times) {
		this.shop = shop;
		this.content = content;
		this.times = times;
	}
	
	public CShop getShop() {
		return shop;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getTimes() {
		return times;
	}
	
	public void setTimes(int times) {
		this.times = times;
	}
	
	public void advertise() {
		CEconomy.getInstance().getServer().broadcastMessage(adMessage());
		this.times--;
	}
	
	private String adMessage() {
		String adTitle = TextFormat.AQUA + "~ ~ ~ ~ ~ �s�i���� ~ ~ ~ ~ ~ \n";
		String randMSG = TextFormat.RESET + (TextFormat.ITALIC + (TextFormat.WHITE + "�C�����l�]�O�ѤF���U�ۤv��������!\n"));
		String separator = TextFormat.RESET + "===============================\n";
		String shopTitle = TextFormat.RESET + (TextFormat.BOLD + (TextFormat.WHITE + "[" + (TextFormat.YELLOW + shop.getName()) + TextFormat.WHITE + "]")) + TextFormat.RESET;
		return adTitle + randMSG + separator + shopTitle + this.content;
		/*
		 * ~ ~ ~ ~ ~ �s�i���� ~ ~ ~ ~ ~ 
		 * �C�����l�]�O�ѤF���U�ۤv��������!
		 * ===============================
		 * 
		 */
	}
}
