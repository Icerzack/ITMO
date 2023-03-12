import * as React from "react";
import Box from "@mui/material/Box";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function PreparationView(props) {
  const { id } = useParams();

  const [complexName, setComplexName] = React.useState("");
  let [effectiveness, setEffectiveness] = React.useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const url = "http://localhost:32456/get/preparation/" + id;
    axios({
      method: "get",
      url: url,
    })
      .then(function (response) {
        let temp = response.data.body;
        setComplexName(temp[0].complexName);
        setEffectiveness(temp[0].effectiveness);
      })
      .catch(function (error) {
        if (error.response) {
          navigate("/sportsman/preparations");
        }
      });
    // setTitle(baaList[0].name);
    // setDescription(baaList[0].description);
    // setManufacturer(baaList[0].manufacturer);
    // setUses(baaList[0].uses);
    // setAllTimeRatingDifference(baaList[0].allTimeRatingDifference);
  }, []);

  let h = window.innerHeight;
  let gridHeight = h - 60;

  return (
    <Box
      sx={{
        height: gridHeight,
      }}
    >
      <Box
        sx={{
          height: "100%",
          display: "flex",
          justifyContent: "center",
          alignItems: "left",
          flexDirection: "column",
          ml: "50px",
          mt: "80px",
        }}
      >
        <img
          src="https://media.istockphoto.com/id/1367321305/photo/preparation-is-the-key.jpg?b=1&s=170667a&w=0&k=20&c=5wEDSTlFt-oiR_oCPxKqeMyloe7-oUZjGYDTWV88Xj0="
          width="200"
          height="300"
          alt="lorem"
        />
        <Box>
          <h1>{complexName}</h1>
        </Box>
        <Box>
          <h3>Эффективность: {effectiveness}</h3>
        </Box>
      </Box>
    </Box>
  );
}
