package application;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadLoadScene extends Thread {
	ResourceLock lock;
	Socket incoming;
	PrintWriter outPrinter;

	ThreadLoadScene(ResourceLock lock, Socket incoming, PrintWriter outPrinter) {
		this.lock = lock;
		this.incoming = incoming;
		this.outPrinter = outPrinter;
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				while (lock.flag != 1) {
					lock.wait();
				}
				Scanner sc = new Scanner(new File("src/application/Login.fxml"));
				Scanner sc2 = new Scanner(new File("src/application/LoginController.java"));
				StringBuilder sb = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				while (sc.hasNext()) {
					sb.append(sc.nextLine() + "\n");
				}
				while (sc2.hasNext()) {
					sb2.append(sc2.nextLine() + "\n");
				}
				outPrinter.write(sb.toString());
				outPrinter.write("DONE \n");
				outPrinter.write(sb2.toString());
				outPrinter.write("FINISHED");
				outPrinter.println();
				Thread.sleep(10);
				lock.flag = 2;
				System.out.println("flag1 "+lock.flag);
				lock.notifyAll();
				sc.close();
				sc2.close();
			}
		} catch (Exception e) {
			System.out.println("Exception 1 :" + e.getMessage());
		}
	}
}
