package com.cshep4.wcpredictor.repository

import com.cshep4.wcpredictor.entity.LeagueEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LeagueRepository : JpaRepository<LeagueEntity, Long>