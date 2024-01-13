package jkproject.soccer.web.common.crawler.service;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import jkproject.soccer.domain.entity.team.Team;
import jkproject.soccer.domain.entity.team.player.Player;
import jkproject.soccer.domain.repository.team.PlayerRepository;
import jkproject.soccer.domain.repository.team.TeamRepository;
import jkproject.soccer.web.common.exception.ApplicationException;
import jkproject.soccer.web.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoScrapService {

	private final PlayerRepository playerRepository;
	private final TeamRepository teamRepository;

	// TODO 메소드 분할 필요
	public void scrapPlayersInfo() {
		System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
		WebDriver driver = new ChromeDriver();

		// 특정 조건이 충족될 때까지 기다리고 NoSuchException을 던진다.
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("https://www.premierleague.com/players");
		// 특정 조건이 충족될 때까지 기다리고 TimeoutExcetpion을 던진다.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 쿠키 허용, 광고 닫기
		WebElement acceptCookieButton = driver.findElement(By.id("onetrust-accept-btn-handler"));
		acceptCookieButton.click();
		WebElement closeAdvertButton = driver.findElement(By.id("advertClose"));
		closeAdvertButton.click();

		// 렌더링할 클럽 설정
		WebElement clubsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
			By.cssSelector(
				"div.dropDown.mobile[data-listen-keypress='true'] div.current[data-dropdown-current='clubs']")));
		clubsButton.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
			By.cssSelector("div.dropDown.mobile.open")));
		WebElement clubContainer = driver.findElement(By.cssSelector("ul.dropdownList[data-dropdown-list='clubs']"));
		List<WebElement> clubOptions = driver.findElements(
			By.cssSelector("ul.dropdownList[data-dropdown-list='clubs'] li"));

		for (WebElement clubOption : clubOptions) {
			String teamName = clubOption.getAttribute("data-option-name");
			Team team = teamRepository.findByName(teamName)
				.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_NAME));
			if (teamName.equals("All Clubs")) {
				continue;
			}

			// 해당 클럽을 찾도록 ul 태그 내에서 스크롤
			scrollToElement(driver, clubContainer, clubOption);

			try {
				clubOption.click();
			} catch (ElementClickInterceptedException e) {
			}

			// 선수상세 페이지 렌더링 기다리기
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("tbody.dataContainer.indexSection div.loader")));
			} catch (TimeoutException e) {
			}
			wait.until(
				ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.player__nationality")));

			// 선수들 정보 스크래핑
			List<WebElement> playerInfoList = driver.findElements(By.cssSelector("tr.player"));

			for (WebElement element : playerInfoList) {
				WebElement ImgAndName = element.findElement(By.className("player__name"));
				String image = ImgAndName.getAttribute("href");
				String name = ImgAndName.getText();
				String position = element.findElement(By.className("player__position")).getText();
				String country = element.findElement(By.className("player__country")).getText();

				Player player = Player.builder()
					.imagePath(image)
					.name(name)
					.position(position)
					.country(country)
					.team(team)
					.build();

				playerRepository.save(player);
			}

			clubsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(
					"div.dropDown.mobile[data-listen-keypress='true'] div.current[data-dropdown-current='clubs']")));
			clubsButton.click();

			// 리스트가 다시 로드될 때까지 기다림
			wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.dropDown.mobile.open")));
		}

		driver.quit();

	}

	private static void scrollToElement(WebDriver driver, WebElement container, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript(
			"arguments[0].scrollTop = arguments[1].offsetTop - arguments[0].offsetTop;",
			container, element
		);
		try {
			Thread.sleep(500); // 컨테이너 스크롤 후 DOM 업데이트 대기 시간
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
