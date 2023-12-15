import "../../css/Main.css";


const Main = () => {

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
                <div class="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/England-flag.png" alt="Aland Islands"/>
                    <p>England</p>
                </div>
                <div class="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Spain-flag.png" alt="Aland Islands"/>
                    <p>Spain</p>
                </div>
                <div class="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Germany-flag.png" alt="Aland Islands"/>
                    <p>Germany</p>
                </div>
            </div>

            League
            <div id="national-logos">
                <div class="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/England-flag.png" alt="Aland Islands"/>
                    <p>England</p>
                </div>
                <div class="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Spain-flag.png" alt="Aland Islands"/>
                    <p>Spain</p>
                </div>
                <div class="country">
                    <img src="../soccer/customicondesign-flags-png/png/32/Germany-flag.png" alt="Aland Islands"/>
                    <p>Germany</p>
                </div>
            </div>
        </>
    );

}

export default Main;