package demo.dao;

import java.util.List;

import demo.entity.Announcement;

public interface AnnouncementDAO {

	
	void save(Announcement announcement);
    void update(Announcement announcement);
    void delete(int id);
    Announcement findById(int id);
    List<Announcement> findAll();
}
