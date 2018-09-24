package cosr.shop.listener;

import java.util.HashMap;

import cmen.essalg.CJEF;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import cosr.economy.CMoney;
import cosr.mcpemail.MailGUI;
import cosr.shop.CShopMain;
import cosr.shop.Sellable;
import cosr.shop.shops.BarterShop;
import cosr.shop.shops.CShop;
import cosr.shop.shops.SoldShop;
import cosr.shop.shops.TitleShop;
import cosr.shop.shops.CShop.OwnerType;
import cosr.shop.shops.LotteryShop;
import cosr.shop.shops.PointShop;
import cosr.shop.shops.PurchaseShop;
import cosr.shop.utils.CAdvertisement;
import cosr.shop.utils.CShopUI;

public class GuiEventListener implements Listener {
	
	public static HashMap<String, CShop> uiPShopMap = new HashMap<String, CShop>();				//���a�g��ۤv���ӤH�ө�
	public static HashMap<String, CShop> uiCShopMap = new HashMap<String, CShop>();				//���a�y�X�L�H�ө�
	public static HashMap<String, Item> uiLotResultMap = new HashMap<String, Item>();
	
	@EventHandler
	public void onForm(PlayerFormRespondedEvent event) {
		Player p = event.getPlayer();
		FormWindow window = event.getWindow();
		FormResponse response = event.getResponse();
		
		if(response == null) {
			flush(p);
			return;
		}
		
		if(window instanceof FormWindowSimple) {
			FormWindowSimple w = (FormWindowSimple)window;
			FormResponseSimple r = (FormResponseSimple)response;
			String btxt = r.getClickedButton().getText();
			if(w.getTitle().equals("�ө�����")) {
				if(btxt.equals(TextFormat.BOLD + "�}�}����")) {
					p.showFormWindow(CShopUI.marketWindow());
				}
				else if(btxt.equals(TextFormat.BOLD + "�ڪ��ө�")) {
					p.showFormWindow(CShopUI.myShopListWindow(p.getName()));
				}
				else if(w.getTitle().equals(TextFormat.BOLD + (TextFormat.GRAY + "��������"))) {
					flush(p);
				}
			}
			else if(w.getTitle().equals(CShop.getShopConfig().getString("svfair_name", "����"))) {
				if(btxt.equals(TextFormat.BOLD + "�ʶR���~")) {
					p.showFormWindow(CShopUI.soldShopListWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�X�⪫�~")) {
					p.showFormWindow(CShopUI.purchaseShopListWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�洫���~")) {
					p.showFormWindow(CShopUI.barterShopListWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "���B���")) {
					p.showFormWindow(CShopUI.lotteryShopListWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�ʶR�ٸ�")) {
					p.showFormWindow(CShopUI.titleShopListWindow(p.getName()));
				}
				else if(btxt.equals(TextFormat.BOLD + "�}���ө�")) {
					p.showFormWindow(CShopUI.pointShopListWindow(p.getName()));
				}else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���"))) {
					p.showFormWindow(CShopUI.homePage());
				}
			}
			else if(w.getTitle().equals("�c��ө�����")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (btxt.equals(CShop.getShopConfig().getString("svfair_name", "���A���ө�����"))? 
							OwnerType.SERVER.getName() : btxt);
					p.showFormWindow(CShopUI.pShopListWindow(ownerName, SoldShop.class));
				}else
					p.showFormWindow(CShopUI.marketWindow());
			}
			else if(w.getTitle().equals("���ʰө�����")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (btxt.equals(CShop.getShopConfig().getString("svfair_name", "���A���ө�����"))? 
							OwnerType.SERVER.getName() : btxt);
					p.showFormWindow(CShopUI.pShopListWindow(ownerName, PurchaseShop.class));
				}else
					p.showFormWindow(CShopUI.marketWindow());
			}
			else if(w.getTitle().equals("�I���ө�����")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (btxt.equals(CShop.getShopConfig().getString("svfair_name", "���A���ө�����"))? 
							OwnerType.SERVER.getName() : btxt);
					p.showFormWindow(CShopUI.pShopListWindow(ownerName, BarterShop.class));
				}else
					p.showFormWindow(CShopUI.marketWindow());
			}
			else if(w.getTitle().equals("����ө�����")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (btxt.equals(CShop.getShopConfig().getString("svfair_name", "���A���ө�����"))? 
							OwnerType.SERVER.getName() : btxt);
					p.showFormWindow(CShopUI.pShopListWindow(ownerName, LotteryShop.class));
				}else
					p.showFormWindow(CShopUI.marketWindow());
			}
			else if(w.getTitle().equals("�ٸ��ө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					int index = Integer.parseInt(btxt.split(" ")[0].replace(TextFormat.ITALIC + "#", ""));
					uiCShopMap.put(p.getName(), CShopMain.getTitleShopMap().get(OwnerType.SERVER.getName()).get(index));
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else
					p.showFormWindow(CShopUI.marketWindow());
			}
			else if(w.getTitle().equals("�}���ө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					int index = Integer.parseInt(btxt.split(" ")[0].replace(TextFormat.ITALIC + "#", ""));
					uiCShopMap.put(p.getName(), CShopMain.getPointShopMap().get(OwnerType.SERVER.getName()).get(index));
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else
					p.showFormWindow(CShopUI.marketWindow());
			}
			else if(w.getTitle().endsWith("���c��ө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (w.getTitle().startsWith(CShop.getShopConfig().getString("svfair_name", "���A��"))? 
							OwnerType.SERVER.getName() : w.getTitle().replace("���c��ө��C��", ""));
					int index = Integer.parseInt(btxt.split(" ")[0].replace(TextFormat.WHITE + "#", ""));
					uiCShopMap.put(p.getName(), CShopMain.getSoldShopMap().get(ownerName).get(index));
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else
					p.showFormWindow(CShopUI.soldShopListWindow(p.getName()));
			}
			else if(w.getTitle().endsWith("�����ʰө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (w.getTitle().startsWith(CShop.getShopConfig().getString("svfair_name", "���A��"))? 
							OwnerType.SERVER.getName() : w.getTitle().replace("���c��ө��C��", ""));
					int index = Integer.parseInt(btxt.split(" ")[0].replace(TextFormat.WHITE + "#", ""));
					uiCShopMap.put(p.getName(), CShopMain.getPurchaseShopMap().get(ownerName).get(index));
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else
					p.showFormWindow(CShopUI.purchaseShopListWindow(p.getName()));
			}
			else if(w.getTitle().endsWith("���I���ө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (w.getTitle().startsWith(CShop.getShopConfig().getString("svfair_name", "���A��"))? 
							OwnerType.SERVER.getName() : w.getTitle().replace("���c��ө��C��", ""));
					int index = Integer.parseInt(btxt.split(" ")[0].replace(TextFormat.WHITE + "#", ""));
					uiCShopMap.put(p.getName(), CShopMain.getBarterShopMap().get(ownerName).get(index));
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else
					p.showFormWindow(CShopUI.barterShopListWindow(p.getName()));
			}
			else if(w.getTitle().endsWith("������ө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
					String ownerName = (w.getTitle().startsWith(CShop.getShopConfig().getString("svfair_name", "���A��"))? 
							OwnerType.SERVER.getName() : w.getTitle().replace("���c��ө��C��", ""));
					int index = Integer.parseInt(btxt.split(" ")[0].replace(TextFormat.WHITE + "#", ""));
					uiCShopMap.put(p.getName(), CShopMain.getLotteryShopMap().get(ownerName).get(index));
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else
					p.showFormWindow(CShopUI.lotteryShopListWindow(p.getName()));
			}
			else if(w.getTitle().equals("�ڪ��ө��C��")) {
				if(!btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܭ���"))) {
					String[] parts = btxt.replace(btxt.split("[")[0]+"[", "").split("] ");
					// [XX�ө�01] �ө��W��  ->  XX�ө�01 & �ө��W��
					if(parts[0].startsWith("���ʰө�")) {
						int index = Integer.parseInt(CJEF.drop(parts[0].replaceAll("0", ""), "���ʰө�"));
						uiPShopMap.put(p.getName(), CShopMain.getPurchaseShopMap().get(p.getName()).get(index));
					}
					else if(parts[0].startsWith("�c��ө�")) {
						int index = Integer.parseInt(CJEF.drop(parts[0].replaceAll("0", ""), "�c��ө�"));
						uiPShopMap.put(p.getName(), CShopMain.getSoldShopMap().get(p.getName()).get(index));
					}
					else if(parts[0].startsWith("�I���ө�")) {
						int index = Integer.parseInt(CJEF.drop(parts[0].replaceAll("0", ""), "�I���ө�"));
						uiPShopMap.put(p.getName(), CShopMain.getBarterShopMap().get(p.getName()).get(index));
					}
					else if(parts[0].startsWith("����ө�")) {
						int index = Integer.parseInt(CJEF.drop(parts[0].replaceAll("0", ""), "����ө�"));
						uiPShopMap.put(p.getName(), CShopMain.getLotteryShopMap().get(p.getName()).get(index));
					}
					p.showFormWindow(CShopUI.myShopSystemWindow(p.getName()));
				}else {
					p.showFormWindow(CShopUI.homePage());
				}
			}
			else {
				if(uiCShopMap.containsKey(p.getName())) {
					CShop shop = uiCShopMap.get(p.getName());
					if(w.getTitle().equals(uiCShopMap.get(p.getName()).getName())) {
						if(btxt.equals(TextFormat.BOLD + "�d�ݰө���T")) {
							p.showFormWindow(CShopUI.shopInfoWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + "���ө��W��")) {
							p.showFormWindow(CShopUI.shopRenameWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + "���X�Ӱө�(�ݪ�O50" + CMoney.name() + ")")) {
							if(Server.getInstance().getLevelByName(shop.getLevelName()) != null) {
								//TODO: ���Ӫ��~
								p.teleport(shop.getPosition());
							}else {
								p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�Ӱө��Ҧb���@�ɦ��G�w�g�����F�O......");
							}
							uiCShopMap.remove(p.getName());
						}
						else if(btxt.equals(TextFormat.BOLD + "�pô�ө��D�H")) {
							MailGUI mailUI = new MailGUI(cosr.mcpemail.Main.getInstance());
							mailUI.mailOutW(p, shop.getOwnerName());
							uiCShopMap.remove(p.getName());
						}
						else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܰө��C��"))) {
							if(shop instanceof PurchaseShop)
								p.showFormWindow(CShopUI.purchaseShopListWindow(p.getName()));
							else if(shop instanceof SoldShop)
								p.showFormWindow(CShopUI.soldShopListWindow(p.getName()));
							else if(shop instanceof BarterShop)
								p.showFormWindow(CShopUI.barterShopListWindow(p.getName()));
							else if(shop instanceof LotteryShop)
								p.showFormWindow(CShopUI.lotteryShopListWindow(p.getName()));
							else if(shop instanceof TitleShop)
								p.showFormWindow(CShopUI.titleShopListWindow(p.getName()));
							else if(shop instanceof PointShop)
								p.showFormWindow(CShopUI.pointShopListWindow(p.getName()));
						}
						else {
							p.showFormWindow(CShopUI.sureToTradeWindow(p.getName()));
						}
					}
				}
				else if(uiPShopMap.containsKey(p.getName())) {
					CShop shop = uiPShopMap.get(p.getName());
					if(w.getTitle().equals(uiPShopMap.get(p.getName()).getName())) {
						if(btxt.equals(TextFormat.BOLD + "�d�ݰө���e��T")) {
							p.showFormWindow(CShopUI.myShopInfoWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + "���ө��W��")) {
							p.showFormWindow(CShopUI.shopRenameWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + "�ǰe�ܸӰө�(�ݪ�O50" + CMoney.name() + ")")) {
							if(Server.getInstance().getLevelByName(shop.getLevelName()) != null) {
								//TODO: ���Ӫ��~
								p.teleport(shop.getPosition());
							}else {
								p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�Ӱө��Ҧb���@�ɦ��G�w�g�����F�O......");
							}
							uiCShopMap.remove(p.getName());
						}
						else if(btxt.equals(TextFormat.BOLD + "�ɳf")) {
							p.showFormWindow(CShopUI.replenishWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + "�������~")) {
							p.showFormWindow(CShopUI.extractWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + "���Ӱө����s�i")) {
							p.showFormWindow(CShopUI.advertiseWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + (TextFormat.RED + "�R���Ӱө�"))) {
							p.showFormWindow(CShopUI.sureToRemoveShopWindow(p.getName()));
						}
						else if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^�ܰө��C��"))) {
							p.showFormWindow(CShopUI.myShopListWindow(p.getName()));
							uiPShopMap.remove(p.getName());
						}
					}
				}
			}
		}
		else if(window instanceof FormWindowCustom) {
			FormWindowCustom w = (FormWindowCustom)window;
			FormResponseCustom r = (FormResponseCustom)response;
			
			if((!uiPShopMap.containsKey(p.getName())) && (!uiCShopMap.containsKey(p.getName()))) return;
			
			if(w.getTitle().equals("���s�R�W�ө�")) {
				CShop shop = null;
				if(GuiEventListener.uiCShopMap.containsKey(p.getName())) {
					shop = GuiEventListener.uiCShopMap.get(p.getName());
				}else if(GuiEventListener.uiPShopMap.containsKey(p.getName())) {
					shop = GuiEventListener.uiPShopMap.get(p.getName());
				}else {
					return;
				}
				String shopName = r.getInputResponse(1);
				shop.setName(shopName);
			}
			else if(w.getTitle().equals("��" + uiPShopMap.get(p.getName()).getName() + TextFormat.RESET + "�ө��ɳf")) {
				CShop shop = uiPShopMap.get(p.getName());
				if(shop instanceof SoldShop || shop instanceof BarterShop) {
					if(!CJEF.isInteger(r.getInputResponse(1))) {
						p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�п�J���T����Ʈ榡");
						p.showFormWindow(CShopUI.replenishWindow(p.getName()));
						return;
					}
					int stock = Integer.parseInt(r.getInputResponse(1));
					if(shop instanceof SoldShop) {
						((SoldShop)shop).replenish(stock);
					}else if(shop instanceof BarterShop) {
						((BarterShop)shop).replenish(stock);
					}
					uiPShopMap.remove(p.getName());
				}
				else if(shop instanceof LotteryShop) {
					if(!CJEF.isInteger(r.getInputResponse(2))) {
						p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�п�J���T����Ʈ榡");
						p.showFormWindow(CShopUI.replenishWindow(p.getName()));
						return;
					}
					LotteryShop lShop = (LotteryShop)shop;
					int i = 0;
					for(Item item : lShop.getStockMap().keySet()) {
						if(i == r.getDropdownResponse(1).getElementID() && 
								r.getDropdownResponse(1).getElementContent().equals(item.getName())) {
							lShop.replenish(item, (int)Long.parseLong(r.getInputResponse(2)));
						}
						i++;
						break;
					}
					p.showFormWindow(CShopUI.replenishWindow(p.getName()));
				}
			}
			else if(w.getTitle().equals("�q" + uiPShopMap.get(p.getName()).getName() + TextFormat.RESET + "�ө����������~")) {
				CShop shop = uiPShopMap.get(p.getName());
				if(shop instanceof SoldShop) {
					if(!CJEF.isInteger(r.getInputResponse(1))) {
						p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�п�J���T����Ʈ榡");
						p.showFormWindow(CShopUI.extractWindow(p.getName()));
						return;
					}
					int stock = Integer.parseInt(r.getInputResponse(1));
					if(shop instanceof SoldShop) {
						((SoldShop)shop).extract(stock);
					}
					uiPShopMap.remove(p.getName());
				}
				else if(shop instanceof LotteryShop || shop instanceof BarterShop) {
					if(!CJEF.isInteger(r.getInputResponse(2))) {
						p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�п�J���T����Ʈ榡");
						p.showFormWindow(CShopUI.extractWindow(p.getName()));
						return;
					}
					FormResponseData dlist = r.getDropdownResponse(1);
					int amount = (int)Long.parseLong(r.getInputResponse(2));
					if(shop instanceof LotteryShop) {
						LotteryShop lShop = (LotteryShop)shop;
						int i = 0;
						for(Item item : lShop.getStockMap().keySet()) {
							if(i == dlist.getElementID() && 
									dlist.getElementContent().equals(item.getName())) {
								lShop.extract(item, amount);
							}
							i++;
							break;
						}
						p.showFormWindow(CShopUI.replenishWindow(p.getName()));
					}
					else if(shop instanceof BarterShop) {
						BarterShop bShop = (BarterShop) shop;
						if(dlist.getElementID() == 0)
							bShop.extractCommodity(amount);
						else
							bShop.extractCurrency(amount);
						uiPShopMap.remove(p.getName());
					}
				}
			}
			else if(w.getTitle().equals("��" + uiPShopMap.get(p.getName()).getName() + TextFormat.RESET + "���s�i")) {
				if(!CJEF.isInteger(r.getInputResponse(2))) {
					p.sendMessage(CShop.infoTitle() + TextFormat.RED + "�п�J���T����Ʈ榡");
					p.showFormWindow(CShopUI.advertiseWindow(p.getName()));
					return;
				}
				String content = r.getInputResponse(1);
				int times = (int) Long.parseLong(r.getInputResponse(2));
				CShopMain.getInstance().getAdTask().getAdList().add(new CAdvertisement(uiPShopMap.get(p.getName()), content, times));
				p.sendMessage(CShop.infoTitle() + TextFormat.GREEN + "���\�s�W�s�i!");
				uiPShopMap.remove(p.getName());
			}
			
		}
		else if(window instanceof FormWindowModal) {
			FormWindowModal w = (FormWindowModal)window;
			FormResponseModal r = (FormResponseModal)response;
			String btxt = r.getClickedButtonText();
			if(w.getTitle().equals("�ө��T��")) {
				if(btxt.equals("��^")) {
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}else if(btxt.equals("�T�{")) {
					CShop shop = uiCShopMap.get(p.getName());
					p.showFormWindow(CShopUI.pShopListWindow(shop.getOwnerName(), shop.getClass()));
					uiCShopMap.remove(p.getName());
				}
			}
			else if(w.getTitle().startsWith("�O�_�T�w")) {
				if(btxt.equals("�T�w")) {
					if(uiCShopMap.containsKey(p.getName())) {
						CShop shop = uiCShopMap.get(p.getName());
						if(shop instanceof PurchaseShop) {
							((PurchaseShop) shop).buyFrom(p);
						}
						else if(shop instanceof Sellable) {
							((Sellable) shop).sellTo(p);
						}
						p.showFormWindow(CShopUI.successfullyTradeWindow(p.getName()));
					}
				}else if(btxt.equals("����")) {
					p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
				}
			}
			else if(w.getTitle().equals("�ʶR���\") || w.getTitle().equals("�X�⦨�\") || 
					w.getTitle().equals("�I�����\") || w.getTitle().equals("������G")) {
				//���ޫ����򳣦^�ө��t�Τ����A���Dx
				p.showFormWindow(CShopUI.shopSystemWindow(p.getName()));
			}
			else if(w.getTitle().startsWith("�ڪ��ө�") && w.getTitle().endsWith("��T")) {
				if(btxt.equals("��^")) {
					p.showFormWindow(CShopUI.myShopSystemWindow(p.getName()));
				}else if(btxt.equals("�T�{")) {
					p.showFormWindow(CShopUI.myShopListWindow(p.getName()));
					uiCShopMap.remove(p.getName());
				}
			}
			else if(w.getTitle().equals("�T�w�R���Ӱө�?")) {
				if(btxt.equals("�T�w")) {
					CShop shop = uiPShopMap.get(p.getName());
					shop.hidePiece();
					if(shop instanceof PurchaseShop) {
						CShopMain.getPurchaseShopMap().get(p.getName()).remove(shop);
					}
					else if(shop instanceof SoldShop) {
						CShopMain.getSoldShopMap().get(p.getName()).remove(shop);
					}
					else if(shop instanceof BarterShop) {
						CShopMain.getBarterShopMap().get(p.getName()).remove(shop);
					}
					else if(shop instanceof LotteryShop) {
						CShopMain.getLotteryShopMap().get(p.getName()).remove(shop);
					}
					uiPShopMap.remove(p.getName());
					p.sendMessage(CShop.infoTitle() + TextFormat.GRAY + "�w���\�R���z���ө�!");
				}else if(btxt.equals("����")) {
					p.showFormWindow(CShopUI.myShopSystemWindow(p.getName()));
				}
			}
		}
	}	
	
	private static void flush(Player p) {
		if(uiPShopMap.containsKey(p.getName()))
			uiPShopMap.remove(p.getName());
		if(uiCShopMap.containsKey(p.getName()))
			uiCShopMap.remove(p.getName());
	}
}
