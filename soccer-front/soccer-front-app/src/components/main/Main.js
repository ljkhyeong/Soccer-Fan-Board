import 'bootstrap/dist/css/bootstrap.min.css';
import "../../css/Main.css";
import {Link} from "react-router-dom";


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
            National Team
            <div id="national-logos">
                <div className="country">
                    <Link to="team/Tottenham">
                    <img src="../soccer/customicondesign-flags-png/png/32/England-flag.png" alt="Aland Islands"/>
                    <p>England</p>
                    </Link>
                </div>
                <div className="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Spain-flag.png" alt="Aland Islands"/>
                    <p>Spain</p>
                </div>
                <div className="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Germany-flag.png" alt="Aland Islands"/>
                    <p>Germany</p>
                </div>
            </div>

            League
            <div id="national-logos">
                <div className="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/England-flag.png" alt="Aland Islands"/>
                    <p>England</p>
                </div>
                <div className="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Spain-flag.png" alt="Aland Islands"/>
                    <p>Spain</p>
                </div>
                <div className="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Germany-flag.png" alt="Aland Islands"/>
                    <p>Germany</p>
                </div>
            </div>
        </>
    );

}

export default Main;