package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection("admin","admin");
		connection.start();
		
		//usando o auto_acknowledge
//		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//usando o client_acknowledge
//		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		
		//usando o session_transacted
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila );

		consumer.setMessageListener(new MessageListener() {

		    @Override
		    public void onMessage(Message message) {

		        TextMessage textMessage = (TextMessage)message;
		        try {
//		            message.acknowledge(); //confirmação do recebimento
		        	System.out.println(textMessage.getText());
//		        	session.commit();
		        	session.rollback();

		        } catch (JMSException e) {
		            e.printStackTrace();
		        }
		    }

		});
		
				
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
