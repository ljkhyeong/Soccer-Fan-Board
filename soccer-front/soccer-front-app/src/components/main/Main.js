import 'bootstrap/dist/css/bootstrap.min.css';
import '../../css/Main.css';
import { Link } from 'react-router-dom';
import { Image } from 'react-bootstrap';

const Main = () => {
    // TODO url 변경없이 컴포넌트 교체만 일어나므로 사용자 입장에서는 북마크, 뒤로가기 작동에서 문제가 있을 수 있다. 수정 요망

    return (
        <>
            <div id="google-logo">
                <h2>SFN - Soccer For Noob</h2>
            </div>

            <div id="search-form">
                <form action="https://www.google.com/search" method="GET" target="_blank">
                    <input type="text" name="q" placeholder="팀 검색" />
                    <input type="submit" value="검색" />
                </form>
            </div>
            <div id="national-logos">
                <div className="country">
                    <Link to="team/arsenal" className="to-club">
                        <img
                            src="https://resources.premierleague.com/premierleague/badges/50/t3.png"
                            alt="Aland Islands"
                        />
                        <p>Arsenal</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/astonvilla" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t7.png" />
                        <p>Aston Villa</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/bournemouth" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t91.png" />
                        <p>BourneMouth</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/brentford" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t94.png" />
                        <p>BrentFord</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/brighton" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t36.png" />
                        <p>Brighton</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/burnley" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t90.png" />
                        <p>Burnley</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/chelsea" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t8.png" />
                        <p>Chelsea</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/crystalpalace" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t31.png" />
                        <p>CrystalPalace</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/everton" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t11.png" />
                        <p>Everton</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/fulham" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t54.png" />
                        <p>Fulham</p>
                    </Link>
                </div>
            </div>
            <div id="national-logos">
                <div className="country">
                    <Link to="team/liverpool" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t14.png" />
                        <p>Liverpool</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/lutontown" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t102.png" />
                        <p>LutonTown</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/mancity" className="to-club">
                        <Image src="https://resources.premierleague.com/premierleague/badges/50/t43.png" />
                        <p>Man-City</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/manu" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t1.png" />
                        <p>Man-United</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/newcastle" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t4.png" />
                        <p>NewCastle</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/nottingham" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t17.png" />
                        <p>Nottingham</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/sheffield" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t49.png" />
                        <p>Sheffield</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/tottenham" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t6.png" />
                        <p>Tottenham</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/westham" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t21.png" />
                        <p>WestHam</p>
                    </Link>
                </div>
                <div className="country">
                    <Link to="team/wolverhampton" className="to-club">
                        <img src="https://resources.premierleague.com/premierleague/badges/50/t39.png" />
                        <p>Wolvers</p>
                    </Link>
                </div>
            </div>
        </>
    );
};

export default Main;
