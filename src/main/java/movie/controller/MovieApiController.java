package movie.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import movie.dto.AnnouncementDto;
import movie.dto.CanReservationDateDto;
import movie.dto.CommentsDto;
import movie.dto.MovieDto;
import movie.dto.ReservationDto;
import movie.dto.ReviewDto;
import movie.dto.SeatDto;
import movie.dto.UserDto;
import movie.service.MovieService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieApiController {

	@Autowired
	MovieService movieService;

	@GetMapping("/api/user")	
	public ResponseEntity<UserDto> currentUserName(Authentication authentication) {
		try {
			System.out.println("asdasd");	
			UserDto userDto = (UserDto) authentication.getPrincipal();
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// 메인페이지
	@GetMapping("/api/mainpage")
	public ResponseEntity<Map<String, Object>> mainpage() throws Exception {
		List<MovieDto> movieDto = movieService.listMovie();
		List<AnnouncementDto> AnnouncementDto = movieService.listAnnouncementDto();
		Map<String, Object> map = new HashMap<>();
		map.put("announcementList", AnnouncementDto);
		map.put("listMovie", movieDto);

		if (movieDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
	}

	// 예약 포스트 버튼

	@PostMapping("api/reservation/")
	public ResponseEntity<String> updateBoard(@RequestBody ObjectNode object, Authentication authentication)
			throws Exception {
		UserDto userDto = (UserDto) authentication.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		ReservationDto reservationDto = mapper.treeToValue(object.get("reservationDto"), ReservationDto.class);
		SeatDto seatDto = mapper.treeToValue(object.get("seatDto"), SeatDto.class);
		reservationDto.setReservationId(userDto.getUserId());
		reservationDto.setReservationName(userDto.getUserName());
		reservationDto.setReservationNumber(userDto.getPhoneNumber());
//			reservationDto.setReservationId(principal.getName());
		int updatedCount = movieService.insertReservation(reservationDto);
		seatDto.setReservationIdx(reservationDto.getReservationIdx());
		movieService.insertSeat(seatDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록에 실패했습니다");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("정상 등록 되었습니다.");
		}
	}


	// 예약 조회

	@GetMapping("api/checkreservationlist/")
	public ResponseEntity<List<ReservationDto>> checkReservationList(Authentication authentication) throws Exception {
		UserDto userDto = (UserDto) authentication.getPrincipal();
		List<ReservationDto> reservationDto = movieService.reservationList(userDto.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(reservationDto);
	}

	@GetMapping("api/checkreservation/{reservationIdx}")
	public ResponseEntity<Map<String, Object>> checkreservation(@PathVariable("reservationIdx") int reservationIdx)
			throws Exception {
		ReservationDto reservationDto = movieService.reservationDetail(reservationIdx);
		List<SeatDto> seatDto = movieService.seatList(reservationIdx);
		MovieDto movieDto = movieService.selectMovieInfo(reservationDto.getMovieIdx());
		Map<String, Object> map = new HashMap<>();
		map.put("reservationDto", reservationDto);
		map.put("seat", seatDto);
		map.put("movieDto", movieDto);
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	// 여기서 영화 고르면
	@GetMapping("api/movie")
	public ResponseEntity<List<MovieDto>> movie() throws Exception {
		List<MovieDto> movieDto = movieService.listMovie();
		if (movieDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(movieDto);
		}
	}

	// 이거는 예약 일
	@GetMapping("api/reservationdate/{movieIdx}")
	public ResponseEntity<List<CanReservationDateDto>> date(@PathVariable("movieIdx") int movieIdx) throws Exception {
		List<CanReservationDateDto> dateDto = movieService.date(movieIdx);
		if (dateDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(dateDto);
		}
	}

	// 선택된 좌석

	@GetMapping("/api/reservedseat/{reservationDate}")
	public ResponseEntity<List<SeatDto>> reservedSeat(@PathVariable("reservationDate") String reservationDate)
			throws Exception {
		List<SeatDto> seatDto = movieService.seatListDate(reservationDate);
		if (seatDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(seatDto);
		}
	}

	// 공지사항 리스트
	@GetMapping("/api/listAnnouncement")
	public ResponseEntity<List<AnnouncementDto>> listAnnouncementDto() throws Exception {
		List<AnnouncementDto> AnnouncementDto = movieService.listAnnouncementDto();
		if (AnnouncementDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(AnnouncementDto);
		}
	}

	// 공지사항 디테일
	@GetMapping("/api/Announcement/{announcementIdx}")
	public ResponseEntity<AnnouncementDto> announcementdetail(@PathVariable("announcementIdx") int announcementIdx)
			throws Exception {
		AnnouncementDto announcementDto = movieService.announcementdetail(announcementIdx);
		if (announcementDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(announcementDto);
		}
	}

	// 한줄평 등록
	@PostMapping("/api/movie/comments/write/{movieIdx}")
	public ResponseEntity<Map<String, Object>> insertcomments(@RequestBody CommentsDto commentsDto,
			@PathVariable("movieIdx") int movieIdx) throws Exception {
		int insertedCount = 0;
		try {
			commentsDto.setMovieIdx(movieIdx);
			insertedCount = movieService.insertComments(commentsDto);
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				movieService.setStarAvg(commentsDto.getMovieIdx());
				result.put("count", insertedCount);
				result.put("commentsIdx", commentsDto.getCommentsIdx());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// 영화 디테일+ 한줄평 리스트
	@GetMapping("/moviedetail/{movieIdx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> movieDetail(@PathVariable("movieIdx") int movieIdx) throws Exception {
		// 리스트 출력하는 메서드 + 영화 제목 출력하는 메서드 호출
		List<CommentsDto> list = movieService.selectCommentsList(movieIdx);
		MovieDto movieDto = movieService.selectMovieInfo(movieIdx);
		// map 사용
		Map<String, Object> result = new HashMap<>();
		// map 사용으로 두 가지 값 넣어줌
		result.put("selectCommentsList", list);
		result.put("movieInfo", movieDto);
		if (result != null && result.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// ---------------리뷰 작성 페이지 mapping 및 등록 여부 메세지 코드 ---------------------
	@PostMapping("/api/movie/review/write/")
	public ResponseEntity<Map<String, Object>> insertReview(@RequestBody ReviewDto reviewDto,
			Authentication authentication) throws Exception {
		int insertedCount = 0;
		try {
			UserDto userDto = (UserDto) authentication.getPrincipal();
			reviewDto.setWriter(userDto.getUserName());
			reviewDto.setWriterId(userDto.getUserId());
			insertedCount = movieService.insertReview(reviewDto);
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);

				result.put("reviewIdx", reviewDto.getReviewIdx());

				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "로그인 해주세요");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}

	}

	// ------------ 영화idx 에 따라 리뷰 리스트 조회하는 코드 ----------------------
	@GetMapping("movie/review/list/")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> openReviewList() throws Exception {
		// 리스트 출력하는 메서드 + 영화 제목 출력하는 메서드 호출
		List<ReviewDto> list = movieService.selectReviewList();
		List<MovieDto> movieDto = movieService.listMovie();
		// map 사용
		Map<String, Object> result = new HashMap<>();
		// map 사용으로 두 가지 값 넣어줌
		result.put("reviewDto", list);
		result.put("movieDto", movieDto);
		if (movieDto != null && movieDto.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// -------------- 리뷰idx 에 따라 리뷰 상세 조회하는 코드------------------------
	@GetMapping("/movie/review/detail/{reviewIdx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> openReviewDetail(@PathVariable("reviewIdx") int reviewIdx ,Authentication authentication)
			throws Exception {
		// 리스트 출력하는 메서드 + 영화 제목 출력하는 메서드 호출
		ReviewDto detail = movieService.selectReviewDetail(reviewIdx);
		MovieDto movieDto = movieService.selectMovieInfo(detail.getMovieIdx());
		UserDto userDto = (UserDto) authentication.getPrincipal();
		// map 사용
		Map<String, Object> result = new HashMap<>();
		// map 사용으로 두 가지 값 넣어줌
		result.put("selectReviewList", detail);
		result.put("movieTitle", movieDto);
		result.put("userDto",userDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// --------------- 리뷰idx 에 따라 리뷰 수정하는 코드 ---------------------
	@PutMapping("/movie/review/update/{reviewIdx}")
	public ResponseEntity<String> updateBoard(@PathVariable("reviewIdx") int reviewIdx,
			@RequestBody ReviewDto reviewDto, Authentication authentication) throws Exception {
		try {
			ReviewDto detail = movieService.selectReviewDetail(reviewIdx);
			UserDto userDto = (UserDto) authentication.getPrincipal();
			if (userDto.getUserId().equals(detail.getWriterId()) || userDto.getUserId().equals("test")) {
				reviewDto.setReviewIdx(reviewIdx);
				int updatedCount = movieService.updateReview(reviewDto);
				if (updatedCount != 1) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body("정상 수정되었습니다");
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자만 수정 가능합니다");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 해주세요");
		}
	}

	// --------------- 리뷰idx 에 따라 리뷰 삭제하는 코드 -----------------------

	@DeleteMapping("/movie/review/delete/{reviewIdx}")
	public ResponseEntity<String> deleteReview(@PathVariable("reviewIdx") int reviewIdx, Authentication authentication)
			throws Exception {
		try {
			UserDto userDto = (UserDto) authentication.getPrincipal();
			ReviewDto detail = movieService.selectReviewDetail(reviewIdx);
			if (userDto.getUserId().equals(detail.getWriterId()) || userDto.getUserId().equals("test") ) {
				int deletedCount = movieService.deleteReview(reviewIdx);
				if (deletedCount != 1) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제에 실패했습니다");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body("정상 삭제되었습니다");
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자만 삭제 가능합니다");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 해주세요");
		}

	}

	// 관리자 : 영화 정보 등록
	@PostMapping("/api/admin/insertmovie")
//	   @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<String, Object>> insertmovie(
			@RequestPart(value = "data", required = false) MovieDto movieDto,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		String UPLOAD_PATH = "/my-app/image/"; 
		int insertedCount = 0;
		String fileNames = "";

		try {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				try {
					File f = new File(UPLOAD_PATH + originFileName);
					mf.transferTo(f);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				movieDto.setPoster(originFileName);
			}
			insertedCount = movieService.insertMovie(movieDto);

			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
				result.put("movieIdx", movieDto.getMovieIdx());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<>();
			System.out.println(e);
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}
	@GetMapping("/api/getImage/{poster}")
	   public void getImage(@PathVariable("poster") String poster, HttpServletResponse response) throws Exception {
	      // reviewImage를 읽어서 전달
	      FileInputStream fis = null;
	      BufferedInputStream bis = null;
	      BufferedOutputStream bos = null;
	      String UPLOAD_PATH = "/my-app/image/";
	      try {
	         response.setHeader("Content-Disposition", "inline;");
	         
	         byte[] buf = new byte[1024];
	         fis = new FileInputStream(UPLOAD_PATH + poster);
	         bis = new BufferedInputStream(fis);
	         bos = new BufferedOutputStream(response.getOutputStream());
	         int read;
	         while((read = bis.read(buf, 0, 1024)) != -1) {
	            bos.write(buf, 0, read);
	         }
	      } finally {
	         bos.close();
	         bis.close();
	         fis.close();
	      }
	   }


	
	
	@PutMapping("api/deletemovie")
	public ResponseEntity<String> deleteMovie(@RequestBody MovieDto movieDto)
			throws Exception {
		int updatedCount = movieService.deleteMoive(movieDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("정상 수정되었습니다");
		}

	}
	@PostMapping("api/inserttime")
	public ResponseEntity<String> inserttime(@RequestBody CanReservationDateDto canReservationDateDto)
			throws Exception {
		int updatedCount = movieService.inserttime(canReservationDateDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("정상 수정되었습니다");
		}

	}
	
	@PostMapping("api/insertannouncement")
	public ResponseEntity<String> insertannouncement(@RequestBody AnnouncementDto announcementDto)
			throws Exception {
		int updatedCount = movieService.insertannouncement(announcementDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("정상 수정되었습니다");
		}

	}

}
