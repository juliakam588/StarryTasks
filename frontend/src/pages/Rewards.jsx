import React from 'react';
import Header from '../components/Header/Header';
import starIcon from '../assets/images/Star-Png-164.png';
import popcornIcon from '../assets/images/popcorn.png';
import snackIcon from '../assets/images/snack.png';
import funDayIcon from '../assets/images/fun-day.png';
import clockIcon from '../assets/images/clock.png';
import jigsawIcon from '../assets/images/jigsaw.png';
import '../assets/styles/Rewards.css';

const RewardItem = ({ imgSrc, name }) => (
  <article className="reward-item">
    <img src={imgSrc} alt={`${name} reward`} className="reward-img" />
    <h3 className="reward-name">{name}</h3>
    <div className="reward-progress">
      <div className="reward-progress-filled"></div>
      <div className="reward-progress-empty"></div>
    </div>
  </article>
);

const RewardsPage = () => {
  const rewards = [
    { name: 'Cinema', imgSrc: popcornIcon },
    { name: 'Favorite snack', imgSrc: snackIcon },
    { name: 'Fun day', imgSrc: funDayIcon },
    { name: 'Going later to sleep', imgSrc: clockIcon },
    { name: 'New toy', imgSrc: jigsawIcon },
  ];

  return (
    <>
      <Header />
      <main className="rewards-page">
        <h2 className="rewards-title">Rewards</h2>
        <section className="stars-container">
          <div className="stars-content">
            <div className="stars-text-col">
              <p className="stars-text">Stars acquired</p>
            </div>
            <div className="stars-count-col">
              <div className="stars-count-wrapper">
                <span className="stars-count">30</span>
                <img src={starIcon} alt="Stars icon" className="stars-img" />
              </div>
            </div>
          </div>
        </section>
        <section className="rewards-list">
          {rewards.map((reward, index) => (
            <RewardItem key={index} name={reward.name} imgSrc={reward.imgSrc} />
          ))}
        </section>
        <button className="exchange-btn">Exchange</button>
      </main>
    </>
  );
};

export default RewardsPage;
