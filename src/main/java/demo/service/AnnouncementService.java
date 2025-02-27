package demo.service;

import java.util.List;

import demo.entity.Announcement;

public interface AnnouncementService {

	void addAnnouncement(Announcement announcement);
    void updateAnnouncement(Announcement announcement);
    void deleteAnnouncement(int id);
    Announcement getAnnouncementById(int id);
    List<Announcement> getAllAnnouncements();
    List<Announcement> getAnnouncementsByPage(int page, int size);
    int getTotalPages(int size);
}
