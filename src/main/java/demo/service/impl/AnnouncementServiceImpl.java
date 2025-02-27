package demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.dao.AnnouncementDAO;
import demo.entity.Announcement;
import demo.service.AnnouncementService;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
    private AnnouncementDAO announcementDao;

    @Override
    public void addAnnouncement(Announcement announcement) {
        announcementDao.save(announcement);
    }

    @Override
    public void updateAnnouncement(Announcement announcement) {
        announcementDao.update(announcement);
    }

    @Override
    public void deleteAnnouncement(int id) {
        announcementDao.delete(id);
    }

    @Override
    public Announcement getAnnouncementById(int id) {
        return announcementDao.findById(id);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementDao.findAll();
    }
    @Override
    public List<Announcement> getAnnouncementsByPage(int page, int size) {
        List<Announcement> allAnnouncements = announcementDao.findAll(); // 獲取所有公告資料
        int totalItems = allAnnouncements.size(); // 總資料筆數

        // 計算起始點和結束點
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalItems);
        
        return allAnnouncements.subList(startIndex, endIndex); // 返回分頁資料
    }
    
    @Override
    public int getTotalPages(int size) {
        List<Announcement> allAnnouncements = announcementDao.findAll();
        int totalItems = allAnnouncements.size();
        return (int) Math.ceil((double) totalItems / size);
    }
}
