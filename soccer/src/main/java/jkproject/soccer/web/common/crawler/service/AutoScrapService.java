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

import jkproject.soccer.domain.repository.team.PlayerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoScrapService {

	private final PlayerRepository playerRepository;

	public void scrapPlayersInfo() {
		System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.premierleague.com/players");

		// 특정 조건이 충족될 때까지 기다리고 TimeoutExcetpion을 던진다.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 쿠키 허용, 광고 닫기
		WebElement acceptCookieButton = driver.findElement(By.id("onetrust-accept-btn-handler"));
		acceptCookieButton.click();
		WebElement closeAdvertButton = driver.findElement(By.id("advertClose"));
		closeAdvertButton.click();

		// 클럽 설정
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
			System.out.println(teamName);
			if (teamName.equals("All Clubs")) {
				continue;
			}

			// 해당 클럽을 찾도록 ul 태그 내에서 스크롤
			scrollToElement(driver, clubContainer, clubOption);

			try {
				clubOption.click();
			} catch (ElementClickInterceptedException e) {
			}

			// 홈페이지 렌더링 기다리기
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("tbody.dataContainer.indexSection div.loader")));
			} catch (TimeoutException e) {
				System.out.println("gogo");
			}

			List<WebElement> elementList = wait.until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("span.player__nationality")));
			List<WebElement> elementList2 = wait.until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tr.player td a.player__name")));

			for (WebElement element : elementList2) {
				System.out.print(element.getText());
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
