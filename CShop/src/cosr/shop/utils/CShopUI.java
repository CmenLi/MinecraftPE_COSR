package cosr.shop.utils;

import java.util.List;

import cmen.essalg.CJEF;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cosr.economy.CMoney;
import cosr.economy.CPoint;
import cosr.shop.CShopMain;
import cosr.shop.ItemSingle;
import cosr.shop.listener.GuiEventListener;
import cosr.shop.shops.BarterShop;
import cosr.shop.shops.CShop;
import cosr.shop.shops.SoldShop;
import cosr.shop.shops.TitleShop;
import cosr.shop.shops.TitleShop.CostType;
import cosr.shop.shops.CShop.OwnerType;
import cosr.shop.shops.LotteryShop;
import cosr.shop.shops.PointShop;
import cosr.shop.shops.PurchaseShop;

public class CShopUI {
	
	private static Config conf = CShop.getShopConfig();
	
	public static FormWindowSimple homePage() {
		FormWindowSimple window = new FormWindowSimple("�ө�����", "");
		window.addButton(new ElementButton(TextFormat.BOLD + "�}�}����"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�ڪ��ө�"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��������")));
		return window;
	}

	/* �}�}���� TODO:Simple */
	public static FormWindowSimple marketWindow() {
		FormWindowSimple window = new FormWindowSimple(conf.getString("svfair_name", "����"), "�п�ܥ������");
		window.addButton(new ElementButton(TextFormat.BOLD + "�ʶR���~"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�X�⪫�~"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�洫���~"));
		window.addButton(new ElementButton(TextFormat.BOLD + "���B���"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�ʶR�ٸ�"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�}���ө�"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���")));
		return window;
	}

	// �X�⫬�ө����� TODO:Simple
	public static FormWindowSimple soldShopListWindow(String viewer) {
		FormWindowSimple window = new FormWindowSimple("�c��ө�����", "�п�ܨ䤤�@�ӥ����h�}�}�a");
		for(String ownerName : CShopMain.getSoldShopMap().keySet()) {
			//List<SoldShop> sShopList = CShopMain.getSoldShopMap().get(ownerName);
			if (!ownerName.equals(viewer) && CShopMain.getSoldShopMap().get(ownerName).size() > 0) {
					window.addButton(new ElementButton((ownerName.equals("Server")? conf.getString("svfair_name", "���A���ө�����") : ownerName)));
			}
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}

	// �ʶR���ө����� TODO:Simple
	public static FormWindowSimple purchaseShopListWindow(String viewer) {
		FormWindowSimple window = new FormWindowSimple("���ʰө�����", "�п�ܨ䤤�@�ӥ����h�}�}�a");
		for (String ownerName : CShopMain.getPurchaseShopMap().keySet()) {
			if (!ownerName.equals(viewer) && CShopMain.getPurchaseShopMap().get(ownerName).size() > 0)
				window.addButton(new ElementButton((ownerName.equals("Server")? conf.getString("svfair_name", "���A���ө�����") : ownerName)));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	// ����ө����� TODO:Simple
	public static FormWindowSimple lotteryShopListWindow(String viewer) {
		FormWindowSimple window = new FormWindowSimple("����ө�����", "�п�ܨ䤤�@�ӥ����h�}�}�a");
		for (String ownerName : CShopMain.getLotteryShopMap().keySet()) {
			if (!ownerName.equals(viewer) && CShopMain.getLotteryShopMap().get(ownerName).size() > 0)
				window.addButton(new ElementButton((ownerName.equals("Server")? conf.getString("svfair_name", "���A���ө�����") : ownerName)));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	// �I���ө����� TODO:Simple
	public static FormWindowSimple barterShopListWindow(String viewer) {
		FormWindowSimple window = new FormWindowSimple("�I���ө�����", "�п�ܨ䤤�@�ӥ����h�}�}�a");
		for (String ownerName : CShopMain.getBarterShopMap().keySet()) {
			if (!ownerName.equals(viewer) && CShopMain.getBarterShopMap().get(ownerName).size() > 0)
				window.addButton(new ElementButton((ownerName.equals("Server")? conf.getString("svfair_name", "���A���ө�����") : ownerName)));
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}

	// �ٸ��ө����� TODO:Simple
	public static FormWindowSimple titleShopListWindow(String viewer) {
		FormWindowSimple window = new FormWindowSimple("�ٸ��ө��C��", "�п�ܨ䤤�@�Ӱө��ݬݧa");
		if(CShopMain.getTitleShopMap().containsKey(OwnerType.SERVER.getName())) {
			if (CShopMain.getTitleShopMap().get(OwnerType.SERVER.getName()).size() > 0) {
				int i = 0;
				for(TitleShop ts : CShopMain.getTitleShopMap().get(OwnerType.SERVER.getName())) {
					window.addButton(new ElementButton(TextFormat.ITALIC + "#" + i + " " + TextFormat.RESET + ts.getName() + 
							(TextFormat.RESET+" (") + TextFormat.BOLD+ts.getTitle().getRarity().getColor()+ts.getTitle().getHead()+(TextFormat.RESET+")")));
					i++;
				}
			}
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	// �}���ө����� TODO:Simple
	public static FormWindowSimple pointShopListWindow(String viewer) {
		FormWindowSimple window = new FormWindowSimple("�}���ө��C��", "�п�ܨ䤤�@�Ӱө��ݬݧa");
		if(CShopMain.getPointShopMap().containsKey(OwnerType.SERVER.getName())) {
			if (CShopMain.getPointShopMap().get(OwnerType.SERVER.getName()).size() > 0) {
				int i = 0;
				for(PointShop pts : CShopMain.getPointShopMap().get(OwnerType.SERVER.getName()))
					window.addButton(new ElementButton(TextFormat.ITALIC + "#" + i + " " + TextFormat.RESET + pts.getName() + 
						(TextFormat.RESET+" (") + TextFormat.LIGHT_PURPLE+pts.getItem().getName()+(TextFormat.RESET+")")));
				i++;
			}
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}
	
	//owner���ө� TODO: Simple
	public static FormWindowSimple pShopListWindow(String owner, Class<?> shopClass) {
		FormWindowSimple window = null;
		if(shopClass.getSimpleName().equalsIgnoreCase("SoldShop")) {
			window = new FormWindowSimple((owner.equals(OwnerType.SERVER.getName())? conf.getString("svfair_name", "���A��") : owner) + 
					"���c��ө��C��", "�п�ܨ䤤�@�Ӱө��ݬݧa");
			List<SoldShop> sShopList = CShopMain.getSoldShopMap().get(owner);
			if (sShopList.size() > 0) {
				for (int i = 0; i < sShopList.size(); i++) {
					SoldShop sShop = sShopList.get(i);
					window.addButton(new ElementButton(
							TextFormat.WHITE + "#" + i + " " + TextFormat.DARK_GRAY + sShop.getName() + 
							(sShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
				}
			}
		}else if(shopClass.getSimpleName().equalsIgnoreCase("PurchaseShop")) {
			window = new FormWindowSimple((owner.equals(OwnerType.SERVER.getName())? conf.getString("svfair_name", "���A��") : owner) + 
					"�����ʰө��C��", "�п�ܨ䤤�@�Ӱө��ݬݧa");
			List<PurchaseShop> pShopList = CShopMain.getPurchaseShopMap().get(owner);
			if (pShopList.size() > 0) {
				for (int i = 0; i < pShopList.size(); i++) {
					PurchaseShop pShop = pShopList.get(i);
					window.addButton(new ElementButton(
							TextFormat.WHITE + "#" + i + " " + TextFormat.DARK_GRAY + pShop.getName() + 
							(pShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
				}
			}
		}else if(shopClass.getSimpleName().equalsIgnoreCase("BarterShop")) {
			window = new FormWindowSimple((owner.equals(OwnerType.SERVER.getName())? conf.getString("svfair_name", "���A��") : owner) + 
					"���I���ө��C��", "�п�ܨ䤤�@�Ӱө��ݬݧa");
			List<BarterShop> bShopList = CShopMain.getBarterShopMap().get(owner);
			if (bShopList.size() > 0) {
				for (int i = 0; i < bShopList.size(); i++) {
					BarterShop bShop = bShopList.get(i);
					window.addButton(new ElementButton(
							TextFormat.WHITE + "#" + i + " " + TextFormat.DARK_GRAY + bShop.getName() + 
							(bShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
				}
			}
		}else if(shopClass.getSimpleName().equalsIgnoreCase("LotteryShop")) {
			window = new FormWindowSimple((owner.equals(OwnerType.SERVER.getName())? conf.getString("svfair_name", "���A��") : owner) + 
					"������ө��C��", "�п�ܨ䤤�@�Ӱө��ݬݧa");
			List<SoldShop> sShopList = CShopMain.getSoldShopMap().get(owner);
			if (sShopList.size() > 0) {
				for (int i = 0; i < sShopList.size(); i++) {
					window.addButton(new ElementButton(
							TextFormat.WHITE + "#" + i + " " + TextFormat.DARK_GRAY + sShopList.get(i).getName()));
				}
			}
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^")));
		return window;
	}

	// ���U�ө� XXX:Simple
	public static FormWindowSimple shopSystemWindow(String playerName) {
		CShop shop = GuiEventListener.uiCShopMap.get(playerName);
		if (shop == null) {
			return new FormWindowSimple("���~", TextFormat.RED + "�䤣��z�Ҿާ@���ө�");
		}
		
		FormWindowSimple window = new FormWindowSimple(shop.getName(), "�п�ܱz�Q��Ӱө����檺�ʧ@");
		window.addButton(new ElementButton(TextFormat.BOLD + "�d�ݰө���T"));
		if(Server.getInstance().isOp(playerName)) {
			window.addButton(new ElementButton(TextFormat.BOLD + "���ө��W��"));
		}
		if (shop instanceof SoldShop) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�ʶR�ӫ~"));
		} else if (shop instanceof PurchaseShop) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�X�⪫�~"));
		} else if (shop instanceof BarterShop) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�I���ӫ~"));
		} else if (shop instanceof LotteryShop) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�ոդ��"));
		} else if (shop instanceof TitleShop) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�ʶR�ٸ�"));
		} else if (shop instanceof PointShop) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�ʶR�}�����~"));
		}
		//TODO: ��O���ӫ~
		window.addButton(new ElementButton(TextFormat.BOLD + "���X�Ӱө�(�ݪ�O50" + CMoney.name() + ")"));
		if (shop.getType().equals(OwnerType.PLAYER)) {
			window.addButton(new ElementButton(TextFormat.BOLD + "�pô�ө��D�H")); // �H�H����
		}
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܰө��C��")));

		return window;
	}

	// �ө��T�� TODO:Modal
	public static FormWindowModal shopInfoWindow(String playerName) {
		CShop shop = GuiEventListener.uiCShopMap.get(playerName);
		if (shop == null) {
			return new FormWindowModal("���~", TextFormat.RED + "�䤣��z�Ҿާ@���ө�", "�T�{", "����");
		}
		FormWindowModal window = new FormWindowModal("�ө��T��", shop.Information(playerName), "�T�{", "��^");
		return window;
	}

	// �T�w��� TODO:Modal
	public static FormWindowModal sureToTradeWindow(String playerName) {
		CShop shop = GuiEventListener.uiCShopMap.get(playerName);
		if (shop == null) {
			return new FormWindowModal("���~", TextFormat.RED + "�䤣��z�Ҿާ@���ө�", "�T�{", "����");
		}
		if (shop instanceof SoldShop) {
			return new FormWindowModal("�O�_�T�w�ʶR?", shop.Information(playerName), "�T�w", "����");
		} else if (shop instanceof PurchaseShop) {
			return new FormWindowModal("�O�_�T�w��X?", shop.Information(playerName), "�T�w", "����");
		} else if (shop instanceof BarterShop) {
			return new FormWindowModal("�O�_�T�w�I��?", shop.Information(playerName), "�T�w", "����");
		} else if (shop instanceof LotteryShop) {
			return new FormWindowModal("�O�_�T�w���?", shop.Information(playerName), "�T�w", "����");
		} else if (shop instanceof TitleShop) {
			return new FormWindowModal("�O�_�T�w�ʶR�Ӻٸ�?", ((TitleShop)shop).getTitle().information(), "�T�w", "����");
		} else if (shop instanceof PointShop) {
			return new FormWindowModal("�O�_�T�w�ʶR?", shop.Information(playerName), "�T�w", "����");
		} else
			return new FormWindowModal("�ө���������", "", "�T�w", "����");
	}

	// ������\ TODO:Modal
	public static FormWindowModal successfullyTradeWindow(String playerName) {
		CShop shop = GuiEventListener.uiCShopMap.get(playerName);
		if (shop == null) {
			return new FormWindowModal("���~", TextFormat.RED + "�䤣��z�Ҿާ@���ө�", "�T�{", "����");
		}
		if (shop instanceof SoldShop) {
			SoldShop sShop = ((SoldShop)shop);
			return new FormWindowModal("�ʶR���\",
					"�z��F" + sShop.getCost() + "���������ʶR" + sShop.getItem().getCount() + "��" + sShop.getItem().getName(), "�T�{", "��^");
		} else if (shop instanceof PurchaseShop) {
			PurchaseShop pShop = ((PurchaseShop)shop);
			return new FormWindowModal("�X�⦨�\",
					"�z��X�F" + pShop.getItem().getCount() + "��" + pShop.getItem().getName() + "����a, ����o�F" + pShop.getCost() + "��������", "�T�{", "��^");
		} else if (shop instanceof BarterShop) {
			BarterShop bShop = ((BarterShop)shop);
			return new FormWindowModal("�I�����\", 
					"�z��X�F" + bShop.getItem().getCount() + "��" + bShop.getItem().getName() + "����a, ����o�F" + 
							bShop.getCurrency().getCount() + "��" + bShop.getCurrency().getName(), "�T�{", "��^");
		} else if (shop instanceof LotteryShop) {
			Item result = GuiEventListener.uiLotResultMap.get(playerName);
			return new FormWindowModal("������G", "���ߧA�⤤�F" + result.getName() + "x" + result.getCount(), "�T�{", "��^");
		} else if (shop instanceof TitleShop) {
			TitleShop tShop = ((TitleShop)shop);
			return new FormWindowModal("�ʶR���\", "�z��F" + tShop.getCost() + 
					(tShop.getCostType().equals(CostType.MONEY)? CMoney.config.getString("name", "����") : CPoint.config.getString("name", "�I��")) + 
					"�ʶR�F�H�U�Ӻٸ�: \n\n" + 
					tShop.getTitle().information(), "�T�{", "��^");
		} else if (shop instanceof PointShop) {
			PointShop ptShop = ((PointShop)shop);
			return new FormWindowModal("�ʶR���\", "�z��F" + ptShop.getCost() + 
					CPoint.config.getString("name", "�I��") + "�ʶR�F" + ptShop.getItem().getCount() + "��" + ptShop.getItem().getName(), "�T�w", "����");
		} else
			return new FormWindowModal("�ө���������", "", "�T�w", "����");
	}

	/* �ڪ��ө� (DONE) TODO:Simple */
	public static FormWindowSimple myShopListWindow(String owner) {
		FormWindowSimple window = new FormWindowSimple("�ڪ��ө��C��", "");
		if(CShopMain.getPurchaseShopMap().containsKey(owner)) {
			List<PurchaseShop> shopList = CShopMain.getPurchaseShopMap().get(owner);
			if(shopList.size() > 0)
				for(int i = 0; i < shopList.size(); i++) {
					PurchaseShop pShop = shopList.get(i);
					window.addButton(new ElementButton(
							TextFormat.BOLD + (TextFormat.BLUE + "[���ʰө�" + CJEF.appendZero(2, i) +"]") + " " + TextFormat.RESET + TextFormat.DARK_GRAY + pShop.getName() + 
							(pShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
				}
		}
			
		if(CShopMain.getSoldShopMap().containsKey(owner)) {
			List<SoldShop> shopList = CShopMain.getSoldShopMap().get(owner);
			if(shopList.size() > 0)
			for(int i = 0; i < shopList.size(); i++) {
				SoldShop sShop = shopList.get(i);
				window.addButton(new ElementButton(
						TextFormat.BOLD + (TextFormat.RED + "[�c��ө�" + CJEF.appendZero(2, i) +"]") + " " + TextFormat.RESET + TextFormat.DARK_GRAY + sShop.getName() + 
						(sShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
			}
		}
			
		if(CShopMain.getBarterShopMap().containsKey(owner)) {
			List<BarterShop> shopList = CShopMain.getBarterShopMap().get(owner);
			if(shopList.size() > 0)
			for(int i = 0; i < shopList.size(); i++) {
				BarterShop sShop = shopList.get(i);
				window.addButton(new ElementButton(
						TextFormat.BOLD + (TextFormat.GOLD + "[�I���ө�" + CJEF.appendZero(2, i) +"]") + " " + TextFormat.RESET + TextFormat.DARK_GRAY + sShop.getName() + 
						(sShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
			}
		}
		
		if(CShopMain.getLotteryShopMap().containsKey(owner)) {
			List<LotteryShop> shopList = CShopMain.getLotteryShopMap().get(owner);
			if(shopList.size() > 0)
			for(int i = 0; i < shopList.size(); i++) {
				LotteryShop sShop = shopList.get(i);
				window.addButton(new ElementButton(
						TextFormat.BOLD + (TextFormat.AQUA + "[����ө�" + CJEF.appendZero(2, i) + "]") + " " + TextFormat.RESET + TextFormat.DARK_GRAY + sShop.getName() + 
						(sShop.isCompleted()? "" : TextFormat.BOLD + (TextFormat.GRAY + "(������)"))));
			}
		}
		
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���")));
		return window;
	}

	/*
	// �ЫحӤH�ө� TODO:Custom
	public static FormWindowCustom newPShopWindow(String builder) {
		FormWindowCustom window = new FormWindowCustom("�ЫحӤH�ө�");
		window.addElement(new ElementInput("�п�J�ө��W��")); // 0
		ElementDropdown tradeTypeList = new ElementDropdown("�п�ܥ������"); // 1
		tradeTypeList.addOption("�c��(sell)");
		tradeTypeList.addOption("����(buy)");
		tradeTypeList.addOption("�I��(barter)");
		tradeTypeList.addOption("���(lottery)");
		window.addElement(tradeTypeList);
		window.addElement(new ElementInput("�Ы��w������~", "Example: 274 5:2 @h")); // 2
		window.addElement(new ElementSlider("�п�J����ƶq", 0, 64, 1)); // 3
		window.addElement(new ElementInput("�п�J�������")); // 4
		return window;
	}
	*/

	// �ڪ��ө��t�� TODO:Simple
	public static FormWindowSimple myShopSystemWindow(String playerName) {
		CShop myShop = GuiEventListener.uiPShopMap.get(playerName);
		if (myShop == null) {
			return new FormWindowSimple("���~", TextFormat.RED + "�䤣��z�Ҿާ@���ө�");
		}
		FormWindowSimple window = new FormWindowSimple(myShop.getName(), "");
		window.addButton(new ElementButton(TextFormat.BOLD + "�d�ݰө���e��T"));
		window.addButton(new ElementButton(TextFormat.BOLD + "���ө��W��"));
		//TODO: ��O���ӫ~
		window.addButton(new ElementButton(TextFormat.BOLD + "�ǰe�ܸӰө�(�ݪ�O50" + CMoney.name() + ")"));
		if(!(myShop instanceof PurchaseShop))
			window.addButton(new ElementButton(TextFormat.BOLD + "�ɳf"));
		window.addButton(new ElementButton(TextFormat.BOLD + "�������~"));
		
		window.addButton(new ElementButton(TextFormat.BOLD + "���Ӱө����s�i"));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.RED + "�R���Ӱө�")));
		window.addButton(new ElementButton(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܰө��C��")));
		return window;
	}

	// �ڪ��ө��T�� TODO:Modal
	public static FormWindowModal myShopInfoWindow(String owner) {
		CShop myShop = GuiEventListener.uiPShopMap.get(owner);
		if (myShop == null) {
			return new FormWindowModal("���~", "", "�T�{", "����");
		}
		FormWindowModal window = new FormWindowModal("�ڪ��ө�" + myShop.getName() + TextFormat.RESET + "��T",
				myShop.Information(owner), "�T�{", "��^");
		return window;
	}
	
	// ���s�R�W�ө� CUSTOM
	public static FormWindowCustom shopRenameWindow(String renamer) {
		CShop shop = null;
		FormWindowCustom window = new FormWindowCustom("���s�R�W�ө�");
		if(GuiEventListener.uiCShopMap.containsKey(renamer)) {
			shop = GuiEventListener.uiCShopMap.get(renamer);
			if(shop.getType().equals(OwnerType.PLAYER) || !Server.getInstance().isOp(renamer)) {
				window.addElement(new ElementLabel("��p, �z���v�������H�ܧ󦹰ө�"));
				return window;
			}
		}else if(GuiEventListener.uiPShopMap.containsKey(renamer)) {
			shop = GuiEventListener.uiPShopMap.get(renamer);
		}else {
			return new FormWindowCustom("Bad ui");
		}
		
		window.addElement(new ElementLabel(shop.Information(renamer)));
		window.addElement(new ElementInput("�п�J�ө��W", "�ө��W��", shop.getName()));
		return window;
	}

	// ��...�ө��ɳf TODO:Custom
	public static FormWindowCustom replenishWindow(String owner) {
		CShop myShop = GuiEventListener.uiPShopMap.get(owner);
		if (myShop == null) {
			return new FormWindowCustom("���~");
		}
		FormWindowCustom window = new FormWindowCustom("��" + myShop.getName() + TextFormat.RESET + "�ө��ɳf");
		String info = "";
		if(myShop instanceof ItemSingle) {
			ItemSingle _myShop = (ItemSingle)myShop;
			info = TextFormat.BOLD + (TextFormat.GREEN + _myShop.getItem().getName()) + TextFormat.RESET
					+ TextFormat.DARK_GREEN + "��e�f�s��: " + TextFormat.RESET + _myShop.getStock() + "\n"
					+ TextFormat.DARK_GREEN + "�@������ƶq: " + TextFormat.RESET + _myShop.getItem().getCount() + "\n";
			if(myShop instanceof BarterShop) {
				BarterShop bShop = (BarterShop)_myShop;
				info += TextFormat.BOLD + (TextFormat.GREEN + bShop.getCurrency().getName()) + TextFormat.RESET
						+ TextFormat.DARK_GREEN + "��e�f�s��: " + TextFormat.RESET + bShop.getCurrencyStock() + "\n"
						+ TextFormat.DARK_GREEN + "�@������ƶq: " + TextFormat.RESET + bShop.getCurrency().getCount() + "\n";
			}
				
			window.addElement(new ElementLabel(info));
		}else {
			if(myShop instanceof LotteryShop) {
				LotteryShop lShop = ((LotteryShop)myShop);
				ElementDropdown itemList = new ElementDropdown("�п�ܸɳf���~"); 
				for(Item item : lShop.getStockMap().keySet()) {
					info += TextFormat.BOLD + (TextFormat.GREEN + item.getName()) + TextFormat.RESET
							+ TextFormat.DARK_GREEN + "��e�f�s��: " + TextFormat.RESET + lShop.getStockMap().get(item) + "\n"
							+ TextFormat.DARK_GREEN + "�@������ƶq: " + TextFormat.RESET + item.getCount() + "\n"
							+ TextFormat.DARK_GREEN + "���~���v: " + TextFormat.RESET + lShop.getProbabilityMap().get(item) + "\n";
					itemList.addOption(item.getName() + "("+item.getCount()+")");
				}
				window.addElement(new ElementLabel(info));
				window.addElement(itemList);
			}
		}
		window.addElement(new ElementInput("�п�J�ɥR�f���ƶq"));
		return window;
	}

	// �q...�ө����������~ TODO:Custom
	public static FormWindowCustom extractWindow(String owner) {
		CShop myShop = GuiEventListener.uiPShopMap.get(owner);
		if (myShop == null) {
			return new FormWindowCustom("���~");
		}
		FormWindowCustom window = new FormWindowCustom("�q" + myShop.getName() + TextFormat.RESET + "�ө����������~");
		String info = "";
		if(myShop instanceof ItemSingle) {
			ItemSingle _myShop = (ItemSingle)myShop;
			info = TextFormat.BOLD + (TextFormat.GREEN + _myShop.getItem().getName()) + TextFormat.RESET
					+ TextFormat.DARK_GREEN + "��e�f�s��: " + TextFormat.RESET + _myShop.getStock() + "\n"
					+ TextFormat.DARK_GREEN + "�@������ƶq: " + TextFormat.RESET + _myShop.getItem().getCount() + "\n";
			if(myShop instanceof BarterShop) {
				BarterShop bShop = (BarterShop)_myShop;
				info += TextFormat.BOLD + (TextFormat.GREEN + bShop.getCurrency().getName()) + TextFormat.RESET
						+ TextFormat.DARK_GREEN + "��e�f�s��: " + TextFormat.RESET + bShop.getCurrencyStock() + "\n"
						+ TextFormat.DARK_GREEN + "�@������ƶq: " + TextFormat.RESET + bShop.getCurrency().getCount() + "\n";
				window.addElement(new ElementLabel(info));
				ElementDropdown itemList = new ElementDropdown("�п�ܴ������~"); 
				itemList.addOption(bShop.getItem().getName());
				itemList.addOption(bShop.getCurrency().getName());
				window.addElement(new ElementLabel(info));
				window.addElement(itemList);
			}else {
				window.addElement(new ElementLabel(info));
			}
		}else {
			if(myShop instanceof LotteryShop) {
				LotteryShop lShop = ((LotteryShop)myShop);
				ElementDropdown itemList = new ElementDropdown("�п�ܴ������~"); 
				for(Item item : lShop.getStockMap().keySet()) {
					info += TextFormat.BOLD + (TextFormat.GREEN + item.getName()) + TextFormat.RESET
							+ TextFormat.DARK_GREEN + "��e�f�s��: " + TextFormat.RESET + lShop.getStockMap().get(item) + "\n"
							+ TextFormat.DARK_GREEN + "�@������ƶq: " + TextFormat.RESET + item.getCount() + "\n"
							+ TextFormat.DARK_GREEN + "���~���v: " + TextFormat.RESET + lShop.getProbabilityMap().get(item) + "\n";
					itemList.addOption(item.getName() + "("+item.getCount()+")");
				}
				window.addElement(new ElementLabel(info));
				window.addElement(itemList);
			}
		}
		window.addElement(new ElementInput("�п�J�������~�ƶq"));

		return window;
	}

	// ��...���s�i TODO:Custom
	public static FormWindowCustom advertiseWindow(String owner) {
		CShop pShop = GuiEventListener.uiPShopMap.get(owner);
		if (pShop == null) {
			return new FormWindowCustom("���~");
		}
		FormWindowCustom window = new FormWindowCustom("��" + pShop.getName() + TextFormat.RESET + "���s�i");
		String info = pShop.Information(owner);
		window.addElement(new ElementLabel(info));
		window.addElement(new ElementInput("�п�J�s�i�H��", "�w����{!!"));
		window.addElement(new ElementInput("�п�J�s�i����", "5"));

		return window;
	}

	// �T�w�R���Ӱө�? TODO:Modal
	public static FormWindowModal sureToRemoveShopWindow(String owner) {
		CShop pShop = GuiEventListener.uiPShopMap.get(owner);
		if (pShop == null) {
			return new FormWindowModal("���~", "", "�T�{", "����");
		}
		String warning = TextFormat.ITALIC + (TextFormat.RED + "! �R����N�L�k��_");
		FormWindowModal window = new FormWindowModal("�T�w�R���Ӱө�?", "", "�T�w", "����");
		window.setContent(warning + "\n" + TextFormat.RESET + pShop.Information(owner));
		return window;
	}
}
