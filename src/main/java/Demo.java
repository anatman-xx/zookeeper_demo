import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.github.zkclient.IZkDataListener;
import com.github.zkclient.ZkClient;
import com.github.zkclient.ZkLock;

public class Demo {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		System.out.println("hello");
		// 创建一个与服务器的连接
		ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 2000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				System.out.println("已经触发了" + event.getType() + "事件！");
			}
		});
		
		System.out.println(new String(zooKeeper.getData("/", false, null)));
		System.exit(0);
//		zooKeeper.create("/test", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		// 创建一个目录节点
		zooKeeper.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		// 创建一个子目录节点
		zooKeeper.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println(new String(zooKeeper.getData("/testRootPath", false, null)));
		// 取出子目录节点列表
		System.out.println(zooKeeper.getChildren("/testRootPath", true));
		// 修改子目录节点数据
		zooKeeper.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
		System.out.println("目录节点状态：[" + zooKeeper.exists("/testRootPath", true) + "]");
		// 创建另外一个子目录节点
		zooKeeper.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println(new String(zooKeeper.getData("/testRootPath/testChildPathTwo", true, null)));
		// 删除子目录节点
		zooKeeper.delete("/testRootPath/testChildPathTwo", -1);
		zooKeeper.delete("/testRootPath/testChildPathOne", -1);
		// 删除父目录节点
		zooKeeper.delete("/testRootPath", -1);
		// 关闭连接
		zooKeeper.close();
		Thread.currentThread().sleep(100000l);
	}
	
	public static void main1(String[] args) {
		ZkClient zkClient = new ZkClient("", 2000);
		zkClient.subscribeDataChanges("", new IZkDataListener() {
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			public void handleDataChange(String arg0, byte[] arg1) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		
		ZkLock eventLock = zkClient.getEventLock();
//		Zk
	}
}
