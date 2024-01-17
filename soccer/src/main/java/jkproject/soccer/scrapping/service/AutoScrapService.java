package jkproject.soccer.scrapping.service;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import jkproject.soccer.player.repository.PlayerRepository;
import jkproject.soccer.team.repository.TeamRepository;
import jkproject.soccer.team.data.entity.Team;
import jkproject.soccer.team.data.entity.player.Player;
import jkproject.soccer.common.exception.ApplicationException;
import jkproject.soccer.common.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoScrapService {

	private final PlayerRepository playerRepository;
	private final TeamRepository teamRepository;

	public void scrapPlayersInfo() {
		System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.premierleague.com/players");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		acceptCookieAndCloseAdvert(driver);

		WebElement clubsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
			By.cssSelector(
				"div.dropDown.mobile[data-listen-keypress='true'] div.current[data-dropdown-current='clubs']")));
		openClubListAndWait(clubsButton, wait);

		// 열린 클럽 리스트 ul, li 가져오기
		WebElement clubContainer = driver.findElement(By.cssSelector("ul.dropdownList[data-dropdown-list='clubs']"));
		List<WebElement> clubOptions = driver.findElements(
			By.cssSelector("ul.dropdownList[data-dropdown-list='clubs'] li"));

		for (WebElement clubOption : clubOptions) {
			String teamName = clubOption.getAttribute("data-option-name");
			if (teamName.equals("All Clubs")) {
				continue;
			}
			Team team = teamRepository.findByName(teamName)
				.orElseThrow(() -> new ApplicationException(ErrorCode.NON_EXISTENT_TEAM_NAME));

			scrollToElementAndClick(driver, clubContainer, clubOption);
			waitPlayerInfo(wait);
			playerInfoToEntityAndSave(driver, team);

			openClubListAndWait(clubsButton, wait);
		}

		driver.quit();

	}

	private static void openClubListAndWait(WebElement clubsButton, WebDriverWait wait) {
		clubsButton.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
			By.cssSelector("div.dropDown.mobile.open")));
	}

	private void playerInfoToEntityAndSave(WebDriver driver, Team team) {
		List<WebElement> playerInfoList = driver.findElements(By.cssSelector("tr.player"));

		for (WebElement playerOption : playerInfoList) {
			WebElement nameAndInfo = playerOption.findElement(By.className("player__name"));
			String info = nameAndInfo.getAttribute("href");
			String name = nameAndInfo.getText();
			String image = playerOption.findElement(By.cssSelector(".img.player__name-image")).getAttribute("src");
			String position = playerOption.findElement(By.className("player__position")).getText();
			String country = playerOption.findElement(By.className("player__country")).getText();

			Player player = Player.builder()
				.infoLink(info)
				.imagePath(image)
				.name(name)
				.position(position)
				.country(country)
				.team(team)
				.build();

			playerRepository.save(player);
		}
	}

	private static void waitPlayerInfo(WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("tbody.dataContainer.indexSection div.loader")));
		} catch (TimeoutException e) {
		}
		wait.until(
			ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.player__nationality")));
	}

	private static void acceptCookieAndCloseAdvert(WebDriver driver) {
		WebElement acceptCookieButton = driver.findElement(By.id("onetrust-accept-btn-handler"));
		acceptCookieButton.click();
		// WebElement closeAdvertButton = driver.findElement(By.id("advertClose"));
		// closeAdvertButton.click();
	}

	private static void scrollToElementAndClick(WebDriver driver, WebElement container, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript(
			"arguments[0].scrollTop = arguments[1].offsetTop - arguments[0].offsetTop;",
			container, element
		);
		try {
			Thread.sleep(500); // 컨테이너 스크롤 후 DOM 업데이트 대기 시간
			element.click();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
