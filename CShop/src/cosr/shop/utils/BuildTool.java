package cosr.shop.utils;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import cosr.shop.ItemSingle;
import cosr.shop.MoneyCostable;
import cosr.shop.shops.BarterShop;
import cosr.shop.shops.CShop;
import cosr.shop.shops.LotteryShop;
import cosr.shop.shops.TitleShop;

public class BuildTool {
	
	public int step = 5;
	private Player builder;
	private CShop shop;
	
	public BuildTool(Player builder) {
		this(builder, null);
	}
	
	public BuildTool(Player builder, CShop shop) {
		this.builder = builder;
		this.shop = shop;
	}
	
	public Player getBuilder() {
		return builder;
	}

	public void setBuilder(Player builder) {
		this.builder = builder;
	}

	public CShop getShop() {
		return shop;
	}

	public void setShop(CShop shop) {
		this.shop = shop;
	}
	
	public void prompt() {
		if(builder == null) return;
		switch(step) {
			case 5: 
				builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�п�ܸӰө�������\n" + TextFormat.WHITE
									+ "@buy      -�ʶR���ө�\n"
									+ "@sell     -�c�⫬�ө�\n"
									+ "@lottery  -������ө�\n"
									+ "@barter   -�洫���ө�\n"
									+ (builder.isOp()? "@point    -�I��}���ө�\n" : "")
									+ (builder.isOp()? "@title    -�ٸ��ө�\n" : "")));
				//if(builder.isOp())step--;
				//else step -= 2;
				break;
			case 4: 
				builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�г]�w�Ӱө����֦���\n" + TextFormat.WHITE
									+ "@p     -�ӤH\n"
									+ "@s     -���A��\n"
									+ "@back  -��^�ܤW�@�ӳ]�w\n"));
				break;
			case 3:
				builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�г]�w�Ӱө����W�r: "));
				break;
			case 2: 
				String title = TextFormat.ITALIC + (TextFormat.YELLOW + "�г]�w�Ӱө��������(��J���Oor���~�I�a)\n");
				String content = TextFormat.WHITE + "";
				if(shop instanceof ItemSingle) {
					content += "@i <���~ID> <����ƶq>  -���w���~ID�Υ���ƶq\n"
							+  "@h <����ƶq>           -�P�_������~���ӫ~ �ë��w����ƶq\n"
							+  "@back                   -��^�ܤW�@�ӳ]�w";
				}else if(shop instanceof LotteryShop) {
					title += TextFormat.RED + "(�z�|��" + ((LotteryShop)shop).getRemain() + "%�����v�|�����t)\n";
					content += "@i <���~ID> <����ƶq> <���v>  -���w���~ID, ����ƶq, ���v\n"
							+  "@h <����ƶq> <���v>           -�P�_������~���ӫ~ �ë��w����ƶq�ξ��v\n"
							+  "@back                         -��^�ܤW�@�ӳ]�w";
				}else if(shop instanceof TitleShop) {
					content += "@t <Head>  -�H�ٸ����Y���w�ٸ�\n"
							+  "@back      -��^�ܤW�@�ӳ]�w";
				}
				builder.sendMessage(title + content);
				break;
			case 1: 
				//TODO: �P�_�H�������B�I��B
				if(shop instanceof MoneyCostable) {
					if(shop instanceof TitleShop) {
						builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�п�J�H�U���O�]�w�������\n"
								+ TextFormat.WHITE + "@c <m:p> <����>  -���w����f��(m������/p���I��)�λ���\n"
								+ TextFormat.WHITE + "@back��^�ܤW�@�ӳ]�w"));
					}else 
						builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "�г]�w�������: "));
				}else {
					if(shop instanceof BarterShop) {
						String title1 = TextFormat.ITALIC + (TextFormat.YELLOW + "�г]�w�Ӱө����I����(��J���Oor���~�I�a)\n");
						String content1 = TextFormat.WHITE + "";
						content1 += "@i <���~ID> <����ƶq>  -���w���~ID�ΧI���һݼƶq\n"
								 +  "@h <����ƶq>           -�P�_������~���I���~ �ë��w�I���һݼƶq\n"
								 +  "@back                   -��^�ܤW�@�ӳ]�w";
						builder.sendMessage(title1 + content1);
					}
				}
				break;
			case 0: 
				builder.sendMessage(TextFormat.ITALIC + (TextFormat.YELLOW + "���I���a�O�]�w�a�I"));
				break;
		}
	}
}
