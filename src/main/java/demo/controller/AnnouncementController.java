package demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import demo.entity.Announcement;
import demo.entity.User;
import demo.service.AnnouncementService;
import demo.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Configuration
@EnableWebMvc
@RequestMapping("/announcement")
public class AnnouncementController { 

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private UserService userService;

	//顯示公告列表頁面
	@GetMapping("")
	public String listAnnouncements(Model model, @RequestParam(defaultValue = "0") int page, // 當前頁數（從 0 開始）
			@RequestParam(defaultValue = "5") int size // 每頁顯示數量
	) {

		List<Announcement> announcements = announcementService.getAnnouncementsByPage(page, size);
		int totalPages = announcementService.getTotalPages(size);
		model.addAttribute("announcements", announcements); // 當前頁面的公告資料
		model.addAttribute("currentPage", page); // 當前頁數
		model.addAttribute("totalPages", totalPages); // 總頁數

		return "announcement-list";
	}

	//進入公告編輯頁面(新增或修改）
	@PostMapping("/edit")
	public String editAnnouncement(@RequestParam(value = "id", required = false) Integer id, Model model) {
		Announcement announcement;
		if (id != null && id > 0) {
			// 若 id 存在，則為修改公告
			announcement = announcementService.getAnnouncementById(id);
			model.addAttribute("announcement", announcement);

		} else {

			model.addAttribute("users", userService.getAllUsers()); // 提供公告者選擇

			return "announcement-new";
		}

		return "announcement-edit";
	}

	//儲存公告（新增或修改）
	@PostMapping("/save")
	public String saveAnnouncement(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam("title") String title,
			@RequestParam("publishDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam("content") String content, @RequestParam("publisher.id") int publisherId,
			@RequestPart(value = "fileUpload", required = false) MultipartFile fileUpload) {
		
		Announcement announcement;

		if (id != null && id > 0) {
			//修改公告
			announcement = announcementService.getAnnouncementById(id);
			if (announcement == null) {
				throw new RuntimeException("找不到公告 ID：" + id);
			}
		} else {
			//新增公告
			announcement = new Announcement();
		}

		
		announcement.setTitle(title);
		announcement.setPublishDate(publishDate);
		announcement.setEndDate(endDate);
		announcement.setContent(content);

		
		User publisher = userService.getUserById(publisherId);
		if (publisher == null) {
			throw new RuntimeException("找不到發佈者 ID：" + publisherId);
		}
		announcement.setPublisher(publisher);

		if (fileUpload != null && !fileUpload.isEmpty()) {
			try {
				byte[] fileData = fileUpload.getBytes(); // 取得檔案內容並轉換為 byte[]
				announcement.setAttachment(fileData);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳失敗：" + e.getMessage());
			}
		}

		
		// 儲存公告（新增或修改）
		if (id != null && id > 0) {
			//修改公告
			announcementService.updateAnnouncement(announcement);
		} else {
			//新增公告
			announcementService.addAnnouncement(announcement);
		}

		return "redirect:/announcement";

	}

	//刪除公告
	@PostMapping("/delete")
	public String deleteAnnouncement(@RequestParam("id") int id) {
		announcementService.deleteAnnouncement(id);
		return "redirect:/announcement";
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Integer id) throws IOException {
		
		//根據ID取得公告
		Announcement announcement = announcementService.getAnnouncementById(id);
		if (announcement == null || announcement.getAttachment() == null) {
			throw new RuntimeException("找不到檔案");
		}

		// 從公告取得檔案的 byte[] 陣列
		byte[] fileBytes = announcement.getAttachment();

		String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(fileBytes));
		String fileExtension = "file"; // 預設副檔名

		if (mimeType != null) {
			switch (mimeType) {
			case "image/jpeg":
				fileExtension = "jpg";
				break;
			case "image/png":
				fileExtension = "png";
				break;
			case "application/pdf":
				fileExtension = "pdf";
				break;
			case "text/plain":
				fileExtension = "txt";
				break;
			case "application/zip":
				fileExtension = "zip";
				break;
			
			}
		}

		// 設定下載檔案的名稱，並設置 header
		String originalFileName = "announcement-" + id; // 檔案的原始名稱
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(
				ContentDisposition.builder("attachment").filename(originalFileName + "." + fileExtension) // 根據 MIME
																											// 類型動態設定副檔名
						.build());

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM) // 下載檔案的MIME類型
				.body(fileBytes); // 傳送檔案的二進位內容

	}

}