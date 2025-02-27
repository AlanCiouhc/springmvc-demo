package demo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import demo.dao.AnnouncementDAO;
import demo.entity.Announcement;

@Repository
@Transactional
public class AnnouncementDAOImpl implements AnnouncementDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Announcement announcement) {
        entityManager.persist(announcement);
    }

    @Override
    public void update(Announcement announcement) {
        entityManager.merge(announcement);
    }

    @Override
    public void delete(int id) {
        Announcement announcement = findById(id);
        if (announcement != null) {
            entityManager.remove(announcement);
        }
    }

    @Override
    public Announcement findById(int id) {
        return entityManager.find(Announcement.class, id);
    }


    @Override
    public List<Announcement> findAll() {
        return entityManager.createQuery("FROM Announcement", Announcement.class).getResultList();
    }

}