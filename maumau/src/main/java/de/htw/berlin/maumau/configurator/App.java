package de.htw.berlin.maumau.configurator;

import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {
        // TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("managerHsqldb");

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            Spieler spieler = new Spieler("hans",1,false);
//			i.setItemId(1L);
//			i.setName("Bobby Car");
//			i.setDescription("toll erhalten");
//			i.setInitialPrice(new MonetaryAmount(BigDecimal.ONE, "EUR"));
//			i.setBids(new HashSet<Bid>());
//
			em.persist(spieler);
            System.out.println(em.find(Spieler.class, 1).getName());

//			Bid b = new Bid(1L, new MonetaryAmount(BigDecimal.ONE, "EUR"), i);
//			i.getBids().add(b);
//			em.persist(b);
//
//			TypedQuery<Object[]> q = em.createQuery("SELECT i, b FROM Item AS i, Bid AS b", Object[].class);
//			List<Object[]> result = q.getResultList();
////			TypedQuery<Bid> q2 = em.createQuery("SELECT b FROM Bid AS b WHERE b.item.initialPrice.currency = 'EUR'",
////					Bid.class);
////			List<Bid> bids = q2.getResultList();
//
////			Item i2 = em.find(Item.class, 1L);
////			Bid b2 = em.find(Bid.class, 1L);
//
//			em.remove(em.find(Bid.class, 1L));
            em.remove(em.find(Spieler.class, 1));
            System.out.println(em.find(Spieler.class, 1).getName());

//			for (Object[] s: result) {
//				System.out.println("Name: " + ((Item) s[0]).getName());
//				System.out.println("description: " + ((Bid) s[1]).getAmount().getAmount());
//			}
////			System.out.println("Anzahl Bids in DB: " + bids.size());

            em.getTransaction().commit();
        } finally {
            em.close();
            entityManagerFactory.close();
        }
    }
}
