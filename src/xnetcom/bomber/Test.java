package xnetcom.bomber;

import xnetcom.bomber.BomberMan.PlayerDirection;

public class Test {

	
	
	
	BomberGame contex;
	public Test (BomberGame contex){
		this.contex=contex;
	}
	public void iniciaText1(){
		Thread hilo = new Thread(new Runnable() {			
			@Override
			public void run() {
				while (true){
					
				
				// TODO Auto-generated method stub
				esperaSec();
				contex.setPlayerDirection(PlayerDirection.RIGHT);
				esperaSec();
				contex.getGameManager().getPolvorin().plantaBomba();
				contex.getGameManager().getPolvorin().ControlRemotoDetonar();
				esperaSec();
				esperaSec();
				esperaSec();
				}
				
				
			}
		});
		hilo.start();
	}
	
	public void esperaSec(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
