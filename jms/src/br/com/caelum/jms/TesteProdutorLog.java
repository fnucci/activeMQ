package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorLog {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection("admin","admin");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("log");
		
		MessageProducer producer = session.createProducer(fila);
		
		
		Message messageInfo = session.createTextMessage("INFO | .....");
		Message messageError = session.createTextMessage("ERROR | .....");
		Message messageWarn = session.createTextMessage("WARN | .....");
		Message messageDebug = session.createTextMessage("DEBUG | .....");
		
		producer.send(messageInfo,DeliveryMode.NON_PERSISTENT, 4, 25000);
		producer.send(messageError,DeliveryMode.NON_PERSISTENT, 8, 25000);
		producer.send(messageWarn,DeliveryMode.NON_PERSISTENT, 6, 25000);
		producer.send(messageDebug,DeliveryMode.NON_PERSISTENT, 2, 25000);
						
		//new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
