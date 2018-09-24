package cosr.friend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cosr.economy.EconomyGUI;
import cosr.friend.api.CSocialAPI;
import cosr.mcpemail.Mail;
import cosr.mcpemail.MailGUI;
import cosr.roleplay.CRolePlay;

public class EventListener implements Listener {

	Map<String, String> player_OtherInForm = new HashMap<String, String>();
	MailGUI mailUi = new MailGUI(new cosr.mcpemail.Main());

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		File file = new File(SocialMain.getInstance().getDataFolder(), player.getName() + ".yml");
		if (!file.exists()) {
			if (!SocialMain.FPOOL.containsKey(player.getName())) {
				SocialMain.FPOOL.put(player.getName(), new ArrayList<String>());
			}
			if (!SocialMain.friendRequestPool.containsKey(player.getName())) {
				SocialMain.friendRequestPool.put(player.getName(), new ArrayList<String>());
			}
			return;
		}

		Config conf = new Config(file, Config.YAML);
		if (conf.exists("mate")) {
			if (!SocialMain.MPOOL.containsKey(player.getName()) && !SocialMain.MPOOL.containsValue(player.getName())) {
				SocialMain.MPOOL.put(player.getName(), conf.getString("mate"));
			}
		}
		if (conf.exists("mood-msg")) {
			if (!SocialMain.msgMap.containsKey(player.getName())) {
				SocialMain.msgMap.put(player.getName(), conf.getString("mood-msg"));
			}
		}

		if (!conf.exists("friends"))
			conf.set("friends", new ArrayList<String>());
		if (!conf.exists("friend_requests"))
			conf.set("friend_requests", new ArrayList<String>());
		conf.save();

		if (!SocialMain.FPOOL.containsKey(player.getName())) {
			SocialMain.FPOOL.put(player.getName(), new ArrayList<String>(conf.getStringList("friends")));
		}
		if (!SocialMain.friendRequestPool.containsKey(player.getName())) {
			SocialMain.friendRequestPool.put(player.getName(),
					new ArrayList<String>(conf.getStringList("friend_requests")));
		}

		int size = SocialMain.friendRequestPool.get(player.getName()).size();
		if (size > 0) {
			player.sendMessage(SocialMain.infoTitle + "�z��e�|��" + size + "�Ӧn���ܽХ��B�z, �п�J/cfriend requests�d�ݩҦ��ܽ�");
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent evnet) {
		Player player = evnet.getPlayer();
		Config conf = new Config(new File(SocialMain.getInstance().getDataFolder(), player.getName() + ".yml"),
				Config.YAML);

		String mate = SocialMain.getMate(player.getName());
		if (mate != null)
			conf.set("mate", mate);
		if (SocialMain.FPOOL.containsKey(player.getName()))
			conf.set("friends", SocialMain.FPOOL.get(player.getName()));
		if (SocialMain.friendRequestPool.containsKey(player.getName()))
			conf.set("friend_requests", SocialMain.friendRequestPool.get(player.getName()));
		if(SocialMain.msgMap.containsKey(player.getName()))
			conf.set("mood-msg", SocialMain.msgMap.get(player.getName()));
		conf.save();
	}
	
	@EventHandler
	public void onTouch(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Item item = event.getItem();
		Item socialItem = CSocialAPI.socialItem();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && item.getId() == 450 &&
				item.getName().equals(socialItem.getName()) && 
				Arrays.equals(item.getLore(), socialItem.getLore())) {
			p.showFormWindow(SocialGUI.homePage());
		}
	}

	@EventHandler
	public void onChat(PlayerChatEvent event) {
		Player sender = event.getPlayer();
		String msg = event.getMessage();
		if (msg.equalsIgnoreCase("@Y") || msg.equalsIgnoreCase("@N")) {
			try {
				String mateName = SocialMain.getMate(sender.getName());
				Player mate = SocialMain.getInstance().getServer().getPlayer(mateName);
				if (SocialMain.breakingSet.contains(mateName)) {
					event.setCancelled();
					if (msg.equalsIgnoreCase("@Y")) {
						sender.sendMessage(TextFormat.RED + "�z�̲׻P�z����Q�F����ĳ, �N�o�����}�F����......");
						CRolePlay.getAchvMap().get("LEARNTOLETGO").grantTo(sender.getName());
						if (mate != null) {
							mate.sendMessage(TextFormat.RED + "�z����Q�̲׻P�z�F����ĳ, �N�o�����}�F����......");
						}
						CRolePlay.getAchvMap().get("LEARNTOLETGO").grantTo(mateName);
					} else if (msg.equalsIgnoreCase("@N")) {
						sender.sendMessage(TextFormat.GRAY + "�z�ڵ��F��责�X������n�D!");
						if (mate != null) {
							mate.sendMessage(TextFormat.GRAY + "�z����Q�̵M���Q���}�z, TA���G�ٷQ�~��d�b�z����O......");
							mate.sendMessage(TextFormat.GRAY + "�Y�z�߷N�w�M, �ЦA�����X����n�D");
						}
					}
					SocialMain.breakingSet.remove(mateName);
				}
			} catch (FileNotFoundException err) {
				// catch
			}
		}
	}

	@EventHandler
	public void onFormResponse(PlayerFormRespondedEvent event) {
		Player p = event.getPlayer();
		FormWindow window = event.getWindow();
		FormResponse response = event.getResponse();

		if (window == null || response == null) {
			// occure when user press the 'x'
			if(player_OtherInForm.containsKey(p.getName()))
				player_OtherInForm.remove(p.getName());
			return;
		}

		if (window instanceof FormWindowSimple) {
			// �����B�B�ͦC��B�B�ͨt�ΡB���B�t��
			String btxt = ((FormResponseSimple) response).getClickedButton().getText();
			if (((FormWindowSimple) window).getTitle().equals("���歺��")) {
				if (btxt.equals(TextFormat.BOLD + "�n�ͦC��")) {
					p.showFormWindow(SocialGUI.friendListWindow(p));
				} else if (btxt.equals(TextFormat.BOLD + "��Q�t��")) {
					if (SocialMain.getMate(p.getName()) == null) {
						p.showFormWindow(SocialGUI.proposingWindow(p.getName()));
					} else
						p.showFormWindow(SocialGUI.marrySystemWindow());
				} else if (btxt.equals(TextFormat.BOLD + "�g�U�߱�")) {
					p.showFormWindow(SocialGUI.moodMsgWindow(p.getName()));
				} else if (btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��������"))) {
					if(player_OtherInForm.containsKey(p.getName()))
						player_OtherInForm.remove(p.getName());
				}
			} else if (((FormWindowSimple) window).getTitle().equals("�n�ͦC��")) {
				if (btxt.equals(TextFormat.BOLD + "�s�W�n��")) {
					p.showFormWindow(SocialGUI.newFriendWindow(p.getName()));
				} else if (btxt.equals(TextFormat.BOLD + "�B�z�n�ͽШD")) {
					p.showFormWindow(SocialGUI.friendRequestWindow(p.getName()));
				} else if (btxt.equals("��^�ܭ���")) {
					p.showFormWindow(SocialGUI.homePage());
				} else {
					String friendName = btxt.split(" ")[0];
					player_OtherInForm.put(p.getName(), friendName);
					p.showFormWindow(SocialGUI.friendWindow(friendName));
				}
			} else if(((FormWindowSimple) window).getTitle().equals("�n�ͥӽЦC��")) {
				try {
					if(btxt.equals(TextFormat.BOLD + (TextFormat.GRAY + "��^"))) {
						p.showFormWindow(SocialGUI.friendListWindow(p));
						return;
					}
					p.showFormWindow(SocialGUI.playerDataWindow(p.getName(), btxt));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else if (((FormWindowSimple) window).getTitle().equals("���A����Q")) {
				String mateName = SocialMain.getMate(p.getName());
				if (btxt.equals("�d�ݦ�Q��T")) {
					try {
						p.showFormWindow(SocialGUI.mateInfoWindow(mateName));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else if (btxt.equals("��TA����")) {
					Player mate = SocialMain.getInstance().getServer().getPlayer(mateName);
					if (mate != null) {
						p.teleport(mate);
					} else
						p.sendMessage(TextFormat.GRAY + "�z����Q�ثe���b�u�W, ���֧�L�Ӫ��a!");
				} else if (btxt.equals("��������")) {
					p.showFormWindow(EconomyGUI.giveMoneyWindow(mateName));
				} else if (btxt.equals("�H�H")) {
					mailUi.mailOutW(p, mateName);
				} else if (btxt.equals(TextFormat.RED + "�Ѱ���Q���Y")) {
					Player mate = SocialMain.getInstance().getServer().getPlayer(mateName);
					if (mate != null) {
						p.sendMessage(TextFormat.GRAY + "�w�e�X���B�ШD, ���ݹ��^��......");
						mate.sendMessage(TextFormat.RED + "�z����Q" + p.getName() + "�V�z���X�F���B�ШD......\n" + "�A��ѫǿ�J@Y�H��ܦP�N\n"
								+ "�A��ѫǿ�J@N��ܩڵ�");
					} else {
						if (SocialMain.isLongTimeNoLogin(mateName)) {
							p.sendMessage(TextFormat.GRAY + "��襼�W�u�ɹj�w�[, �L�������B���\");

							new Mail(TextFormat.RED + "GM", mateName, TextFormat.RED + "�t�ΫH��",
									TextFormat.RED + "���a" + p.getName() + "���X�F���B�n�D\n" + TextFormat.GRAY
											+ "�ѩ�z�Ӥ[�S���W�u, ���F���@���a�v�Q, �w�L����۰����B\n\n" + "COSR�ζ� �q�W").sendOut();

							SocialMain.breakingSet.remove(p.getName());
							SocialMain.MPOOL.remove(p.getName());
						} else {
							p.sendMessage(TextFormat.GRAY + "�z����Q���b�u�W, ���B����H�P�N!");
						}
					}
				} else if (btxt.equals("��^�ܭ���")) {
					p.showFormWindow(SocialGUI.homePage());
				}
			} else {
				// �n�ͨt�Τ���
				if (((FormWindowSimple) window).getTitle().equals(player_OtherInForm.get(p.getName()))) {
					String friendName = ((FormWindowSimple) window).getTitle();
					if (btxt.equals("�d�ݭӤH��T")) {
						try {
							p.showFormWindow(SocialGUI.friendInfoWindow(friendName));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} else if (btxt.equals("��������")) {
						p.showFormWindow(EconomyGUI.giveMoneyWindow(friendName));
					} else if (btxt.equals("�H�H")) {
						mailUi.mailOutW(p, friendName);
					} else if (btxt.equals(TextFormat.RED + "�Ѱ��n�����Y")) {
						p.showFormWindow(SocialGUI.sureToDelFriendWindow(friendName));
					} else if (btxt.equals("��^�ܦn�ͦC��")) {
						p.showFormWindow(SocialGUI.friendListWindow(p));
					}
				}
			}
		} else if (window instanceof FormWindowCustom) {
			String title = ((FormWindowCustom) window).getTitle();
			FormResponseCustom responseCustom = ((FormResponseCustom) response);
			// �s�W�B�͡B�e�X�i�աB�����B�H�H�B�]�w�߱��p�y
			if (title.equals("�s�W�n��")) {
				String playerName = (responseCustom.getInputResponse(0).equals(""))
						? responseCustom.getDropdownResponse(1).getElementContent()
						: responseCustom.getInputResponse(0);
				if (!playerName.equals("None")) {
					if (!playerName.equals(p.getName())) {
						try {
							p.showFormWindow(SocialGUI.playerDataWindow(p.getName(), playerName));
							player_OtherInForm.put(p.getName(), playerName);
						} catch (FileNotFoundException e) {
							p.sendMessage(TextFormat.RED + "�䤣��Ӫ��a");
						}
					}
				}
				responseCustom = null;
			} else if (title.equals("�z�٨S����Q, ���֧�@��TA�a")) {
				String targetName = responseCustom.getDropdownResponse(0).getElementContent();

				if (!targetName.equals("None")) {
					Player target = SocialMain.getInstance().getServer().getPlayer(targetName);

					if (SocialMain.proposingPool.containsValue(p.getName())) {
						p.sendMessage(TextFormat.GRAY + "��p! �@�H�L�k�P�ɦV�h�ӤH�D�B��");
						p.sendMessage(TextFormat.YELLOW + "�O�A�T�ߤG�N�F, ����VTA��ܨǤ���a!");
						return;
					}

					if (SocialMain.MPOOL.containsValue(targetName) || SocialMain.MPOOL.containsKey(targetName)) {
						p.sendMessage(TextFormat.GRAY + "�ӹ�H�w�g���t���o, �L�k�VTA�D�B�S......");
						return;
					}
					if (SocialMain.proposingPool.containsKey(targetName)) {
						p.sendMessage(TextFormat.GRAY + "�ӹ�H�w�g����L�H���b�D�B�o, �ٽеy��O!");
						p.sendMessage(TextFormat.YELLOW + "(�p����: �H�ʫH���L, ���L���D�A���߷N�a)");
						return;
					}

					if (target != null) {
						SocialMain.proposingPool.put(targetName, p.getName());
						target.sendMessage(TextFormat.ITALIC
								+ (TextFormat.YELLOW + "���a" + p.getName() + "�V�z�D�B�F!! >///<\n") + TextFormat.RESET
								+ "��J/cmarry accept ����TA���߷N><\n" + TextFormat.RESET + "��J/cmarry deny  ����TA���߷NQQ");
					} else {
						p.sendMessage(TextFormat.GRAY + "�ӹ�H���G���b�u�W, ��TA�W�u�F�A�V�L��F�߷N�a!");
					}
					responseCustom = null;
				}
			} else if (title.startsWith("�������a") && title.endsWith("����")) {
				if (player_OtherInForm.containsKey(p.getName())) {
					player_OtherInForm.remove(p.getName());
				}
				responseCustom = null;
			} else if (title.startsWith("�H�H�� ")) {
				String playerName = title.replace("�H�H�� ", "").trim();
				String topic = responseCustom.getInputResponse(0);
				String content = responseCustom.getInputResponse(1);

				new Mail(p.getName(), playerName, topic, content).sendOut();
				if (player_OtherInForm.containsKey(p.getName())) {
					player_OtherInForm.remove(p.getName());
				}
				p.sendMessage(TextFormat.GREEN + "���\�o�e�H��" + TextFormat.RESET + playerName + TextFormat.GREEN + "���a");
				responseCustom = null;
			} else if (title.equals("�g�U���誺�߱�")) {
				SocialMain.msgMap.put(p.getName(), responseCustom.getInputResponse(0));
				p.sendMessage(TextFormat.GREEN + "�߱��p�y�]�w���\!");
				responseCustom = null;
			}
			System.gc();
		} else if (window instanceof FormWindowModal) {
			// ���a��T�B��Q��T�B�T�{�R���n�͡B�T�{�Ѱ���Q
			String title = ((FormWindowModal) window).getTitle();
			FormResponseModal responseModal = (FormResponseModal) response;
			String btxt = responseModal.getClickedButtonText();
			String otherName = player_OtherInForm.get(p.getName());
			if(title.startsWith("���a") && title.endsWith("���ӤH�ɮ�")) {
				if(btxt.equals("�[���n��")) {
					CSocialAPI.makeFriend(p.getName(), otherName);
				} else if(btxt.equals("�ڵ��n�ͥӽ�")) {
					if(SocialMain.getFriendRequests(p.getName()) != null) {
						if(SocialMain.getFriendRequests(p.getName()).contains(otherName)) {
							p.sendMessage(TextFormat.GRAY + "�z�ڵ��F" + otherName + "���n���ܽ�");
							SocialMain.getFriendRequests(p.getName()).remove(otherName);
							player_OtherInForm.remove(p.getName());
							return;
						}
					}
					p.sendMessage(TextFormat.GRAY + "�L�Ӫ��a���n���ܽ�");
				} else if(btxt.equals("����")) {
					if(player_OtherInForm.containsKey(p.getName())) player_OtherInForm.remove(p.getName());
				}
			} else if (title.equals("�n��: " + player_OtherInForm.get(p.getName()))) {
				if (btxt.equals("��^") || btxt.equals("����")) {
					// �Yfalse-button��r����^�A�h�N��������friendInfo
					// �Yfalse-button��r�������A�h�N��������sureToDelFriend
					// �H�W��̬Ҷ���^�B�ͨt�ε���
					p.showFormWindow(SocialGUI.friendWindow(otherName));
				} else if (btxt.equals("�T�{")) {
					// �T�{�R���n��
					ArrayList<String> friendList = SocialMain.FPOOL.get(p.getName());
					if (friendList.contains(otherName)) {
						friendList.remove(otherName);

						if (SocialMain.FPOOL.containsKey(otherName)) {
							SocialMain.FPOOL.get(otherName).remove(p.getName());
						} else {
							File file = new File(SocialMain.getInstance().getDataFolder(), otherName + ".yml");
							if (!file.exists()) {
								p.sendMessage(TextFormat.RED + "�䤣��Ӫ��a");
								return;
							}

							Config conf = new Config(file);
							if (!conf.exists("friends")) {
								conf.set("friends", new ArrayList<String>());
							}
							if (!conf.getStringList("friends").contains(p.getName())) {
								p.sendMessage(TextFormat.RED + "�z���b�Ӫ��a���n�ͦW�椺");
								return;
							}
							conf.getStringList("friends").remove(p.getName());
							conf.save();
						}
						p.sendMessage(TextFormat.GRAY + "�w�N���a" + otherName + "�q�z���n�ͦW�椤����");

					} else {
						p.sendMessage(TextFormat.RED + "�Ӫ��a���b�z���n�ͦW�椺");
					}
				}
			} else if (title.equals("��Q: " + SocialMain.getMate(p.getName()))) {
				String mateName = SocialMain.getMate(p.getName());
				if (btxt.equals("��^")) {
					p.showFormWindow(SocialGUI.marrySystemWindow());
				} else if (btxt.equals("�T�{")) {
					if (mateName != null) {
						SocialMain.breakingSet.add(p.getName());
						p.sendMessage(TextFormat.RED + "�z�u���n���}TA�F��...?");
						Player mate = SocialMain.getInstance().getServer().getPlayer(mateName);

						if (mate != null) {
							p.sendMessage(TextFormat.GRAY + "�w�e�X���B�ШD, ���ݹ��^��......");
							mate.sendMessage(TextFormat.RED + "�z����Q" + p.getName() + "�V�z���X�F���B�ШD......\n"
									+ "�A��ѫǿ�J@Y�H��ܦP�N\n" + "�A��ѫǿ�J@N��ܩڵ�");
						} else {
							if (SocialMain.isLongTimeNoLogin(mateName)) {
								p.sendMessage(TextFormat.GRAY + "��襼�W�u�ɹj�w�[, �L�������B���\");

								new Mail(TextFormat.RED + "GM", mateName, TextFormat.RED + "�t�ΫH��",
										TextFormat.RED + "���a" + p.getName() + "���X�F���B�n�D\n" + TextFormat.GRAY
												+ "�ѩ�z�Ӥ[�S���W�u, ���F���@���a�v�Q, �w�L����۰����B\n" + "�ڭ̹惡�P���p, �Y�z��������D�β�ĳ, �w���pô�ڭ�\n\n"
												+ TextFormat.RESET + "COSR�ζ� �q�W").sendOut();

								SocialMain.breakingSet.remove(p.getName());
								SocialMain.MPOOL.remove(p.getName());
							} else {
								p.sendMessage(TextFormat.GRAY + "�z����Q���b�u�W, ���B����H�P�N!");
							}
						}
					} else
						p.sendMessage(TextFormat.GRAY + "�z��e�٨S����Q, ���֥h��@�ӧa(#");
				}
			}
		}
	}
}
