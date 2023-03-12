import TopAppBar from "./components/top-bar";
import { Outlet } from "react-router-dom";
import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function App() {
  const navigate = useNavigate();
  useEffect(() => {
    localStorage.clear("id");
    localStorage.clear("username");
    localStorage.clear("password");

    if (localStorage.getItem("id") === null) {
      navigate("/login");
    } else {
      const id = localStorage.getItem("id");
      const url = "/sportsman/" + id;
      navigate(url);
    }
  }, []);

  return (
    <div>
      <div className="App">
        <TopAppBar></TopAppBar>
      </div>
      <div className="Content">
        <Outlet></Outlet>
      </div>
    </div>
  );
}

export default App;
