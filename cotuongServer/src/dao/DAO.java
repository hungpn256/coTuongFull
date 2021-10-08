/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author phamhung
 */
public class DAO {

    public static Session session;

    public DAO() {
        if (session == null) {
            try {
                session = new Configuration().configure(new File("src/resource/hibernate.cfg.xml"))
                        .buildSessionFactory().openSession();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
